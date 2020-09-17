let vue = new Vue({
    el :"#vue-apartment",
    data : {
        apartment : {},
        apartmentLoaded : false,
        apartmentImages : []
    },
    methods : {
        loadData : function() {
            let addressBarTokens = window.location.href.split("/");
            let apartmentID = addressBarTokens[addressBarTokens.length-1];

            rest.getApartment(apartmentID)
                .then(function(response) {
                    if (response.data) {
                        Vue.set(vue, "apartment", response.data);
                        Vue.set(vue, "apartmentLoaded", true);

                        Array.prototype.forEach.call(vue.apartment.images, imageID => {
                            rest.getImage(imageID)
                            .then(response => {
                                if (response.data) {
                                    vue.apartmentImages.push(response.data.base64_string);
                                }
                            })
                        })
                    }
                })
        },
        getImage : function(imageID) {
            return axios.get("/WebProject/data/images/" + imageID)
        },
        getApartment : function(apartmentID) {
            return axios.get("/WebProject/data/apartments/" + apartmentID)
        },
        activate : function() {
            let requestWrapper = {
                stringKey : vue.apartment.key
            }
            axios.put("http://localhost:8080/WebProject/data/apartments/activate", requestWrapper)
                .then(function(response) {
                    if (response.status == 200) {
                        Vue.set(vue, "apartment", response.data);
                    }
                    else {
                        alert("Failed to activate apartment.");
                    }
                })
        },
        swapPhotos(smallImageID) {
            let smallImage = document.getElementById(smallImageID);
            let bigImage = document.getElementById("img1");

            
            if (smallImage.src  &&  bigImage.src) {
                let tmp = bigImage.src
                bigImage.src = smallImage.src
                smallImage.src = tmp
            }
            else {
                console.log("Cannot swap an empty image")
            }
        },
        deactivate : function() {
            let requestWrapper = {
                stringKey : vue.apartment.key
            }
            axios.put("http://localhost:8080/WebProject/data/apartments/deactivate", requestWrapper)
                .then(function(response) {
                    if (response.status == 200) {
                        Vue.set(vue, "apartment", response.data);
                    }
                    else {
                        alert("Failed to activate apartment.");
                    }
                })
        }, 
        edit : function() {
            window.location.href = "http://localhost:8080/WebProject/apartments/edit/" + vue.apartment.key;
        },
        goToReviews : function() {
            let addressBarTokens = window.location.href.split("/");
            let apartmentID = addressBarTokens[addressBarTokens.length-1];

            window.location.href = "/WebProject/reviews/" + apartmentID
        }
    },
    beforeMount() {
        this.loadData();
    }
});
