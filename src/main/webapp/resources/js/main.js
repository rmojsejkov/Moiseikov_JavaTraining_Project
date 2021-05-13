"use strict";

const elementSelector = {
    'string': child => document.createTextNode(child),
    'function': child => document.createElement(child),
    'object': child => child
}

/**
 *
 * @param option {object}
 * @returns {HTMLDivElement}
 */
const node = function (option) {
    const type = option.type ? option.type : 'div';
    const element = document.createElement(type);

    if (option.children) {
        if (option.children instanceof Array) {
            option.children.forEach(child => element.appendChild(elementSelector[typeof child](child)));
        } else {
            element.appendChild(elementSelector[typeof option.children](option.children));
        }
    }

    if (option.href) {
        element.href = option.href;
    }

    if (option.value) {
        element.value = option.value;
    }

    if (option.selected) {
        element.selected = option.selected;
    }

    if (option.inputType) {
        element.type = option.inputType;
    }

    element.onclick = option.onclick ? (e) => option.onclick(e) : element.onclick;
    element.onload = option.onload ? option.onload : element.onload;

    if (typeof element.onload === "function") {
        element.onload();
    }

    if (option.classList) {
        option.classList.forEach(c => element.classList.add(c));
    }

    if (option.id) {
        element.id = option.id;
    }

    if (option.name) {
        element.name = option.name;
    }

    if (option.for) {
        element.htmlFor = option.for;
    }

    if (option.placeholder) {
        element.placeholder = option.placeholder;
    }

    if (option.disabled) {
        element.disabled = option.disabled;
    }

    if (option.styles) {
        const optionStyles = option.styles;
        const styles = element.style;
        if (optionStyles.background) {
            styles.background = optionStyles.background;
        }
        if (optionStyles.color) {
            styles.color = optionStyles.color;
        }
    }

    return element;
}

const Label = content => node({
    classList: ['label'],
    children: content
})

const LabelDate = content => node({
    classList: ['label', 'date'],
    children: content
})

const ItemContent = play => node({
    classList: ['item-content'],
    children: [
        node({
            classList: ['column'],
            children: [
                Label('Author: ' + play.author.name),
                Label('Genre: ' + play.genre.name),
            ]
        }),
        node({
            classList: ['column'],
            children: play.dates.map(date => LabelDate(date.playsDate.day + '.' + date.playsDate.month))
        })
    ]
})

function loadPlays() {
    fetch('/data-api/plays')
        .then(response => response.json())
        .then(plays => {
            const catalog = document.getElementById('catalog-auto');
            catalog.innerHTML = '';
            plays.forEach(play => catalog.appendChild(node({
                classList: ['car-item'],
                children: [
                    node({
                        type: 'span',
                        id: 'play-' + play.id
                    }),
                    node({
                        classList: ['title'],
                        children: play.name
                    }),
                    ItemContent(play)
                ]
            })))
        })
}


const loginRegEx = /^[a-zA-z]{1}[a-zA-Z1-9_]{3,20}$/i;
const passRegEx = /^[a-zA-z]{1}[a-zA-Z0-9_/!/@/#/$/%/&]{3,20}$/i;
const emailRegEx = /^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/;

let msgColor = document.getElementsByClassName("msg")[0].style.color;
let submenus = Array.from(document.getElementsByClassName("menu"));

let modals;


const init = function init() {
    modals = Array.from(document.getElementsByClassName("modal"));

    setValidation();

    submenus.forEach(function(submenu, index, submenus) {
        submenu.addEventListener("click", openMenu)
    });

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        modals.forEach(function(modal, index, modals) {
            if (event.target === modal) {
                modal.style.display = "none";
                clearForms();
            }
        });
    };

};

init();

function openedForm() {
    if (document.getElementById("login-form").style.display !== "none" &&
        document.getElementById("login-form").style.display !== "") {
        return document.getElementById("login-form");
    } else if (document.getElementById("registration-form").style.display !== "none" &&
        document.getElementById("registration-form").style.display !== "") {
        return document.getElementById("registration-form");
    } else return null;
}


function openMenu() {
    let submenu = this.getElementsByClassName("submenu")[0];
    if (submenu.style.transform === "scaleY(1)") {
        submenu.style.transform = "scaleY(0)";
    } else {
        submenu.style.transform = "scaleY(1)";
    }
}


function setZeroFirstFormat(value)
{
    if (value < 10)
    {
        value='0' + value;
    }
    return value;
}

function getDateTime() {
    let currentDateTime = new Date();
    let day = setZeroFirstFormat(currentDateTime.getDate());
    let month = setZeroFirstFormat(currentDateTime.getMonth()+1);
    let year = currentDateTime.getFullYear();
    let hours = setZeroFirstFormat(currentDateTime.getHours());
    let minutes = setZeroFirstFormat(currentDateTime.getMinutes());
    let seconds = setZeroFirstFormat(currentDateTime.getSeconds());

    return day + "." + month + "." + year + " " + hours + ":" + minutes + ":" + seconds;
}

function dateFormat(currentDateTime) {
    let day = setZeroFirstFormat(currentDateTime.getDate());
    let month = setZeroFirstFormat(currentDateTime.getMonth()+1);
    let year = currentDateTime.getFullYear();
    let hours = setZeroFirstFormat(currentDateTime.getHours());
    let minutes = setZeroFirstFormat(currentDateTime.getMinutes());

    return day + "." + month + "." + year + " " + hours + ":" + minutes;
}

function clearForms() {
    let forms = Array.from(document.querySelectorAll('input'));
    let msgs = Array.from(document.querySelectorAll('.msg'));

    forms.forEach((form, index, forms) => form.value = "");

    msgs.forEach((msg, index, msgs) => msg.innerHTML = "");

}

function isLoginValidation(login) {
    return loginRegEx.test(login);
}

function isPassValidation(password) {
    return passRegEx.test(password);
}

function isEmailValidation(email) {
    return emailRegEx.test(email);
}

function setValidation() {
    let loginForms = Array.from(document.getElementsByName("login"));
    let passForms = Array.from(document.querySelectorAll("input[type='password']"));
    let emailForms = Array.from(document.getElementsByName("email"));
    //TODO: update logic! Concat inputs and check names.
    emailForms.forEach((form, index, emailForms) => {
        form.addEventListener("change", function() {
            if (!emailRegEx.test(this.value)) {
                this.style.color = "red";
            } else {
                this.style.color = "black";
            }
        });
    });

    passForms.forEach((form, index, passForms) => {
        form.addEventListener("change", function() {
            if (!passRegEx.test(this.value)) {
                this.style.color = "red";
            } else {
                this.style.color = "black";
            }
        });
    });

    loginForms.forEach((form, index, loginForms) => {
        form.addEventListener("change", function() {
            if (!loginRegEx.test(this.value)) {
                this.style.color = "red";
            } else {
                this.style.color = "black";
            }
        });
    });
}

class UserService {
    static _instance;
    constructor() {
        this.user = null;
        fetch("/data-api/auth")
            .then(response => {
                if (!response.ok) {
                    throw new Error("User undefined");
                }
                return response.json();
            }).then(user => this.setUser(user))
            .catch(reason => console.log(reason));
    }

    static getInstance() {
        if (!this._instance) {
            this._instance = new UserService();
        }
        return this._instance;
    }

    logIn() {
        const loginDOM = document.querySelector('#login-form input[name="login"]');
        const passwordDOM = document.querySelector('#login-form input[name="password"]');

        const login = loginDOM.value;
        const password = passwordDOM.value;

        fetch('/data-api/login', {
            method: 'POST',
            body: JSON.stringify({
                login, password
            })
        }).then(response => {
            if (!response.ok) {
                throw new Error("Not found user");
            }
            return response.json();
        }).then(user => this.setUser(user))
            .catch(reason => {
                const msgDOM = document.getElementById('login-msg');
                msgDOM.innerText = "Wrong login or password";
            })
    }

    setUser(user) {
        this.user = user;
        const statusUserDOM = document.getElementById("login");
        statusUserDOM.innerText = user.login;
        const userSidebar = document.getElementById('user-sidebar');
        userSidebar.innerHTML = '';
        const fullNameUser = node({
            type: 'li',
            children: user.fullName
        });
        const logOutBtn = node({
            type: 'li',
            children: 'Logout',
            onclick: () => this.logoutUser()
        });
        userSidebar.appendChild(fullNameUser);
        userSidebar.appendChild(logOutBtn);

        document.getElementById('login-form').style.display = 'none';
    }

    logoutUser() {
        fetch('/data-api/logout', {
            method: 'POST'
        }).then(response => {
            if (!response.ok) {
                throw Error(response.message)
            }
            this.user = null;
            return response.json()
        }).then(response => {
            const statusUserDOM = document.getElementById("login");
            statusUserDOM.innerText = "Guest";
            const userSidebar = document.getElementById('user-sidebar');
            userSidebar.innerHTML = '';
            const loginBtn = node({
                type: 'li',
                onclick: () => document.getElementById('login-form').style.display='block',
                children: 'Log In'
            });
            const regBtn = node({
                type: 'li',
                onclick: () => document.getElementById('registration-form').style.display='block',
                children: 'Sign Up'
            });
            userSidebar.appendChild(loginBtn);
            userSidebar.appendChild(regBtn);
        })
    }
}

const loginBtn = document.getElementById("login-button");

function logInUser() {
    UserService.getInstance().logIn()
}

UserService.getInstance();
loadPlays();
