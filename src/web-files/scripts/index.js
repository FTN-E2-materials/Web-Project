new Vue({
    el :"#vue-login-register",
    data : {
        name : "Nikola",
        website : "http://www.google.rs",
        age : 10,
        apartments : [],
        apartmentID : ""
    },
    methods : {
        addYear : function() {
            this.age++;
        },
        removeYear : function() {
            this.age--;
        },
        enterAge : function() {
            console.log("You touched something");
        },
        openLogin : function() {
            axios.get("http://localhost:8080/WebProject/login")
        },
        openRegistration : function() {
            axios.get("http://localhost:8080/WebProject/register")
        },
        getApartments : function() {
            axios.get("http://localhost:8080/WebProject/data/apartments")
                .then(function(response){
                    alert(response);
                    this.apartments = response.data
                    alert(this.apartments);
                    console.log(this.apartments);

                    Array.prototype.forEach.call(this.apartments, apartment => {
                        console.log(apartment);
                    })
                })
        },
        addReview : function() {
            axios.post("http://localhost:8080/WebProject/data/reviews", {
                id: "review101",
                text: "Very nice place. Would visit again.",
                rate: 5,
                authorID: "1",
                apartmentID: "Apartment4",
                visibleToGuests: true
            }).then(function() {
                alert("Postovao sam.")
            })
        },
        searchApartment : function() {
            if (this.apartmentID === "") {
                return;
            }
            axios.get("http://localhost:8080/WebProject/data/apartments/" + this.apartmentID)
                .then(function(result) {
                    console.log(result.data);
                })
        }
    } 
});
