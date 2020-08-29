let vue = new Vue({
    el :"#vue-apartments",
    data : {
        apartments : [],
        query : "",
        holder : [],
        noApartmentsFound : false,
        dateStart : new Date(),
        dateEnd : new Date()
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
        printDates : function() {
            console.log("Starting date: " + vue.dateStart);
            console.log("Ending date: " + vue.dateEnd);
        },
        getApartments : function() {
            axios.get("http://localhost:8080/WebProject/data/apartments/")
                .then(function(response) {
                    if (response.status == 200) {
                        Vue.set(vue, "apartments", response.data)
                    }
                    else {
                        alert("Couldn't load apartments.")
                    }
                })
        },
        goToApartment : function(apartmentID) {
            axios.get("http://localhost:8080/WebProject/data/apartments/" + apartmentID)
                .then(function(response) {
                    if (response.status == 200) {
                        console.log(response.data);
                    }
                    else {
                        alert("Couldn't load apartments.")
                    }
                })
        },
    },
    beforeMount() {
        this.getApartments();
        this.holder = new Array();
    }
});
