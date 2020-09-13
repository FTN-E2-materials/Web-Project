let vue = new Vue({
    el :"#vue-apartments",
    data : {
        apartments : [],
        apartmentsLoaded : false,
        query : "",
        holder : [],
        noApartmentsFound : false,
        dateStart : new Date(),
        dateEnd : new Date(),
        city : "",
        postalCode : "",
        minPrice : "",
        maxPrice : "",
        numOfGuests : "",
        minRooms : "",
        maxRooms : "",
        filtersOpened : false
    },
    components: {
        vuejsDatepicker
    },
    methods : {
        searchApartments : function() {
            if (this.query) {    // Do not query on empty 
                axios.get("http://localhost:8080/WebProject/data/apartments/search/?name=" + this.query)
                    .then(function(response) {
                        if (response.status == 200) {
                            if (vue.holder.length == 0) {
                                vue.holder = vue.apartments;   // Store all the previously downloaded apartments
                                console.log("Storing previous list...");
                            }
                            Vue.set(vue, "apartments", response.data);

                            if (vue.apartments.length == 0) {
                                vue.noApartmentsFound = true;
                            }
                            else {
                                vue.noApartmentsFound = false;
                            }
                        }
                        else {
                            alert("Couldn't search apartments.")
                        }
                    })
            }
            else {
                if (vue.holder.length > 0){
                    Vue.set(vue, "apartments", vue.holder);
                    vue.noApartmentsFound = false;
                }
            }
        },
        getApartments : function() {
            axios.get("http://localhost:8080/WebProject/data/apartments/")
                .then(function(response) {
                    if (response.status == 200) {
                        Vue.set(vue, "apartments", response.data)
                        Vue.set(vue, "apartmentsLoaded", true)
                    }
                    else {
                        alert("Couldn't load apartments.")
                    }
                })
        },
        goToApartment : function(apartmentID) {
            window.location.href = "http://localhost:8080/WebProject/apartments/" + apartmentID
        },
        filter : function() {
            let filterWrapper = {
                city : {
                    name : this.city,
                    postalCode : this.postalCode,
                },
                numOfGuests : this.numOfGuests,
                minRooms : this.minRooms,
                maxRooms : this.maxRooms,
                minPrice : this.minPrice,
                maxPrice : this.maxPrice
            }

            axios.post("http://localhost:8080/WebProject/data/apartments/filter", filterWrapper)
                .then(function(response) {
                    if (response.status == 200) {
                        if (response.data.length == 0) {
                            vue.noApartmentsFound = true;
                            Vue.set(vue, "apartments", response.data)
                        }
                        else {
                            vue.noApartmentsFound = false;
                            if (vue.holder.length == 0) {
                                Vue.set(vue, "holder", vue.apartments)
                            }
                            Vue.set(vue, "apartments", response.data)
                        }
                        
                    }
                })
                .catch(error => {
                    console.log(error.response.data)
                })
        },
        clearFilters : function() {
            vue.city = ""
            vue.postalCode = ""
            vue.minPrice = ""
            vue.maxPrice = ""
            vue.minRooms = ""
            vue.maxRooms = ""
            vue.numOfGuests = ""
        },
        closeFilters : function() {
            Vue.set(vue, "filtersOpened", false);
        },
        openFilters : function() {
            Vue.set(vue, "filtersOpened", true);
        }
    },
    beforeMount() {
        this.getApartments();
        this.holder = new Array();
    }
});
