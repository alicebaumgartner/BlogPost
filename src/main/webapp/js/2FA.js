/**
 * view-controller for 2fa-form
 */
const userRole = getCookie("userRole");
document.addEventListener("DOMContentLoaded", () => {
    showNav(userRole);
    showSecret();
    document.getElementById("2faForm").addEventListener("submit", twoFAUser);
});

function twoFAUser(event) {
    event.preventDefault();
    showMessage("", "info");

    fetch("./resource/user/2fa",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "Authorization": "Bearer " + readStorage("token")
            },
            body: "secret=" + document.getElementById("secret").value
        })
        .then(function (response) {
            if (!response.ok) {
                showMessage("Falsches Geheimwort", "error");
            } else if (response.status === 401) {
                window.location.href = "./";}
            else loginSuccess(response)
        })
        .catch(function (error) {
            console.log(error);
        });
}


/**
 * saves the JWToken and redirects
 * @param response
 */
function loginSuccess(response) {
    saveToken(response.headers);
    window.location.href = "./Blogpost.html";
}

/**
 * shows the number of the secret word to enter
 */
function showSecret() {
    const index = getCookie("secret");
    if (index != null) {
        document.getElementById("index").innerText = index;
    } else {
        window.location.href = "./index.html";
    }


    /**
     * shows the navigation
     * @param userRole
     */
    function showNav(userRole) {
        const navbar = document.getElementById("nav");
        let text = "<ul>";
        if (!userRole || userRole === "guest") {
            text += "<li><a href='./index.html'>Anmelden</a></li>";
        } else {
            text += "<li><a href='./Blogpost.html'>Blogpost</a></li>";

        }
        text += "<li id='message' style='color: red;'></li>" +
            "</ul>";
        navbar.innerHTML = text;
    }
}