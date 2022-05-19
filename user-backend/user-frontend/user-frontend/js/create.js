const btnBack = document.querySelector(".btn-back")
const API_URL = "http://localhost:8080/users"
//Tạo user mới
const nameEl = document.getElementById("fullname")
const emailEl = document.getElementById("email")
const phoneEl = document.getElementById("phone")
const passwordEl = document.getElementById("password")
const btnSave = document.getElementById("btn-save")
btnBack.addEventListener("click", function () {
    //Điều hướng trong js
    window.location.href = "/user-frontend/"
})

//Lấy Danh sách tỉnh thành phố
const getCity = async () => {
    try {
        let res = await axios.get(`https://provinces.open-api.vn/api/p/`)
        renderCity(res.data)
    } catch (error) {
        console.log(error)
    }
}
const addressEl = document.getElementById("address")
const renderCity = arr => {
    let html = ""
    for (let i = 0; i < arr.length; i++) {
        const d = arr[i]
        html += `<option value="${d.name}">${d.name}</option>`
    }
    addressEl.innerHTML = html
}
getCity()



btnSave.addEventListener("click", async function () {
    try {
        let res = await axios.post(API_URL, {
            name: nameEl.value,
            email: emailEl.value,
            phone: phoneEl.value,
            address: addressEl.value,
            password: passwordEl.value
        })
        console.log(res)
        if (res.data) {
            window.location.href = "/user-frontend/"
        }
    } catch (error) {
        console.log(error)
    }

})
