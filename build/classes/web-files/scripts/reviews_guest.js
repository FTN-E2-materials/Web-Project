let vue = new Vue({
    el :"#vue-reviews",
    data : {
        reviews : [],
        apartment : undefined,
        apartmentLoaded : false,
        reviewsLoaded : false,
        qualifiedForReview : false,
        formVisible : false,
        reviewText : "",
        reviewRating : "",
        apartmentImage : ""
    },
    methods : {
        createReview : function() {
            let tokens = window.location.href.split("/");
            let apartmentID = tokens[tokens.length-1]

            let review = {
                text : vue.reviewText,
                rating : 5,
                apartmentID : apartmentID
            }

            axios.post("/WebProject/data/reviews", review)
                .then(response => {
                    if (response.status == 200) {
                        window.location.href = ""
                    }
                })
                .catch(error => {
                    alert(error.response.data)
                })
        },
        getReviews : function() {
            let tokens = window.location.href.split("/");
            let apartmentID = tokens[tokens.length-1]

            axios.get("/WebProject/data/reviews/" + apartmentID)
                .then(response => {
                    if (response.status === 200) {
                        Vue.set(vue, "reviews", response.data);
                        vue.reviewsLoaded = true;
                    }
                })
                .catch (error => {
                    alert(error.response.data)
                })
        },
        getApartment : function() {
            let tokens = window.location.href.split("/");
            let apartmentID = tokens[tokens.length-1]

            axios.get("/WebProject/data/apartments/" + apartmentID)
                .then(response => {
                    if (response.status === 200) {
                        response.data.rating = Math.round(response.data.rating * 100)/100
                        Vue.set(vue, "apartment", response.data);
                        vue.apartmentLoaded = true;

                        vue.getImage(response.data.mainImage)
                            .then(response => {
                                if (response.data) {
                                    vue.apartmentImage = response.data.base64_string
                                }
                            })
                    }
                })
                .catch (error => {
                    alert(error.response.data)
                })
        },
        getImage : function(imageID) {
            return axios.get("/WebProject/data/images/" + imageID)
        },
        isQualifiedForReview : function() {
            let tokens = window.location.href.split("/");
            let apartmentID = tokens[tokens.length-1]

            let wrapper = {
                stringKey : apartmentID
            }

            axios.post("/WebProject/data/reviews/permission", wrapper)
                .then(response => {
                    if (response.status == 200) {
                        Vue.set(vue, "qualifiedForReview", true)
                    }
                })
                .catch (error => {
                    alert(error.response.data)
                    Vue.set(vue, "qualifiedForReview", false)
                })
        },
        openForm : function() {
            Vue.set(vue, "formVisible", true);
        },
        closeForm : function() {
            Vue.set(vue, "formVisible", false);
        }
    },
    beforeMount() {
        this.getReviews();
        this.getApartment();
        this.isQualifiedForReview();
    }
});
