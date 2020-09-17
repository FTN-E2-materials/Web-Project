let vue = new Vue({
    el :"#vue-apartments",
    data : {
        apartments : [],
        apartmentsLoaded : false,
        query : "",
        holder : [],
        dateStart : new Date(),
        dateEnd : new Date(),
        city : "",
        postalCode : "",
        minPrice : "",
        maxPrice : "",
        numOfGuests : "",
        minRooms : "",
        maxRooms : "",
        filtersOpened : false,
        calendarStart : undefined,
        calendarEnd : undefined,
        errorMsg : "",
        calendarErrorMsg : "",
        calendarRendered : false,
        calendarsVisible : false,
        apartmentImages : new Map()
    },
    components: {
        vuejsDatepicker
    },
    methods : {
        getApartments : function() {
            axios.get("http://localhost:8080/WebProject/data/apartments/")
                .then(function(response) {
                    if (response.status == 200) {
                        Array.prototype.forEach.call(response.data, apartment => {
                            apartment.rating = Math.round(apartment.rating * 100)/100
                        })
                        Vue.set(vue, "apartments", response.data)
                        Vue.set(vue, "apartmentsLoaded", true)
                        vue.getApartmentImages()
                    }
                    else {
                        alert("Couldn't load apartments.")
                    }
                })
        },
        getApartmentImages : function() {
            Array.prototype.forEach.call(vue.apartments, apartment => {

                this.getImage(apartment.mainImage)
                    .then(response => {
                        if (response.status == 200) {
                            Vue.set(vue, "apartmentImages", new Map(vue.apartmentImages.set(apartment.key, response.data.base64_string)))
                        }
                })
            })
        },
        getImage : function(imageID) {
            return axios.get("/WebProject/data/images/" + imageID)
        },
        goToApartment : function(apartmentID) {
            window.location.href = "/WebProject/apartments/" + apartmentID
        },
        openNewTab : function(apartmentID) {
            window.open("/WebProject/apartments/" + apartmentID, "_blank")
        },
        filter : function() {
            vue.calendarErrorMsg = "";
            /** Do checks of everything here!  */
            let sDate = (vue.calendarStart.value == null ? 
                                        {
                                            ticks : -1
                                        } : 
                                        {
                                            ticks : vue.calendarStart.value.getTime()
                                        })
            let eDate = (vue.calendarEnd.value == null ?  
                                    {
                                        ticks : -1
                                    } : 
                                    { 
                                        ticks : vue.calendarEnd.value.getTime()
                                    })

            let filterWrapper = {
                city : {
                    name : this.city,
                    postalCode : this.postalCode,
                },
                numOfGuests : this.numOfGuests,
                minRooms : this.minRooms,
                maxRooms : this.maxRooms,
                minPrice : this.minPrice,
                maxPrice : this.maxPrice,
                startingDate : sDate,
                endingDate : eDate
            }

            axios.post("http://localhost:8080/WebProject/data/apartments/filter", filterWrapper)
                .then(function(response) {
                    if (response.status == 200) {
                        if (response.data.length == 0) {
                            vue.calendarErrorMsg = "No apartments found for your criteria."
                            Vue.set(vue, "apartments", response.data)
                        }
                        else {
                            if (vue.holder.length == 0) {
                                Vue.set(vue, "holder", vue.apartments)
                            }
                            Vue.set(vue, "apartments", response.data)
                        }
                        
                    }
                })
                .catch(error => {
                    vue.calendarErrorMsg = "Please enter valid filter values."
                })
        },
        clearFilters : function() {
            vue.calendarErrorMsg = "";

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
            vue.calendarsVisible = false;
            vue.calendarRendered = false;
        },
        openFilters : function() {
            Vue.set(vue, "filtersOpened", true);
            vue.calendarsVisible = true;
        },
        sortByLowestPrice() {
            let sortFunction = function(a,b){
                if (a.pricePerNight < b.pricePerNight) {
                    return -1;
                }
                if (a.pricePerNight > b.pricePerNight) {
                    return 1;
                }
                return 0;
            }

            vue.apartments.sort(sortFunction);
        },
        sortByHighestPrice() {
            let sortFunction = function(a,b){
                if (a.pricePerNight < b.pricePerNight) {
                    return 1;
                }
                if (a.pricePerNight > b.pricePerNight) {
                    return -1;
                }
                return 0;
            }

            vue.apartments.sort(sortFunction);
        }
    },
    beforeMount() {
        this.getApartments();
        this.holder = new Array();

        this.calendarStart = new ej.calendars.Calendar({
            min: new Date((new Date()).getTime() + 86400000),
            value: new Date((new Date()).getTime() + 86400000),
            showTodayButton : false
        });

        this.calendarEnd = new ej.calendars.Calendar({
            min: new Date(this.calendarStart.value.getTime() + 86400000),
            showTodayButton : false
        });
    },
    updated() {
        if (vue.calendarRendered === false  &&  vue.calendarsVisible === true){
            vue.calendarRendered = true
            this.calendarStart.appendTo('#elementStart');
            this.calendarEnd.appendTo('#elementEnd');
        }
    } 
});
