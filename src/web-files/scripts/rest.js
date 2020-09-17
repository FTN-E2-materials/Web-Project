let rest = new Vue({
    data : {

    },
    methods : {
        getApartments() {
            return axios.get("/WebProject/data/apartments")
        },
        getApartment(apartmentID) {
            return axios.get("/WebProject/data/apartments/" + apartmentID)
        },
        updateApartment(apartment) {
            return axios.put("/WebProject/data/apartments", apartment)
        },
        /** Deletes an apartment, whose ID is packed into the RequestWrapper [stringKey] field. */
        deleteApartment(wrapperApartmentID) {
            return axios.post("/WebProject/data/apartments/delete", wrapperApartmentID)
        },
        getImage(imageID) {
            return axios.get("/WebProject/data/images/" + imageID)
        },
        /** Returns an axios promise to GET all reservations from the server, which the current client can see. */
        getReservations() {
            return axios.get("/WebProject/data/reservations")
        },
        getReviews(apartmentID) {
            return axios.get("/WebProject/data/reviews/" + apartmentID)
        },
        getAmenities() {
            return axios.get("/WebProject/data/amenities")
        },
        createAmenity(amenity) {
            return axios.post("/WebProject/data/amenities", amenity)
        },
        /** Deletes an amenity whose ID is packed into the RequestWrapper [stringKey] field */
        deleteAmenity(wrapperAmenityID) {
            return axios.post("/WebProject/data/amenities/delete", wrapperAmenityID)
        },
        updateAmenity(amenity) {
            return axios.put("/WebProject/data/amenities", amenity)
        },
        createHost(host) {
            return axios.post("/WebProject/data/users")
        },
        getUsers() {
            return axios.get("/WebProject/data/users")
        },

    }
})