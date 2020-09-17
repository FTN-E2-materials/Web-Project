let sortVue = new Vue({
    methods : {
        reservationsLowestFirst() {
            let sortFunction = function(a,b){
                if (a.price < b.price) {
                    return -1;
                }
                if (a.price > b.price) {
                    return 1;
                }
                return 0;
            }
        },
        reservationsHighestFirst() {
            let sortFunction = function(a,b){
                if (a.price < b.price) {
                    return 1;
                }
                if (a.price > b.price) {
                    return -1;
                }
                return 0;
            }
        },
        apartmentsLowestFirst() {
            let sortFunction = function(a,b){
                if (a.pricePerNight < b.pricePerNight) {
                    return -1;
                }
                if (a.pricePerNight > b.pricePerNight) {
                    return 1;
                }
                return 0;
            }
        },
        apartmentsHighestFirst() {
            let sortFunction = function(a,b){
                if (a.pricePerNight < b.pricePerNight) {
                    return 1;
                }
                if (a.pricePerNight > b.pricePerNight) {
                    return -1;
                }
                return 0;
            }
        }
    }
})