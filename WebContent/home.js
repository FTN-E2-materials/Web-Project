$(document).ready(function(){

        alert("Starting apartment get.");

        $.get({
            url : "u/apartments",
                contentType : "application/json",
                success : function(data){
                    if (data) {
                        alert("I am about to display some data");
                        data.forEach(apartment => {
                            alert(apartment.id)
                        });
                    }
                    else {
                        alert("No data available.")
                    }
                },
                error : function(data){
                    alert("An error occurred!");
                }
            });
});