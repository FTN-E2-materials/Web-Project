new Vue({
    el :"#vue-app",
    data : {
        name : "Nikola",
        website : "http://www.google.rs",
        age : 10,
        apartments : []
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
        getApartments : function() {
            axios.get("http://localhost:8080/WebProject/u/apartments")
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
            axios.post("http://localhost:8080/WebProject/u/reviews", {
                id: "review101",
                text: "Very nice place. Would visit again.",
                rate: 5,
                authorID: "1",
                apartmentID: "Apartment4",
                visibleToGuests: true
            }).then(function() {
                alert("Postovao sam.")
            })
        }
    } 
});
