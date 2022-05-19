const API_URL = "http://localhost:8080/users"

const params = new URLSearchParams(window.location.search)
const id = params.get("id")// Lấy ID từ URL

const nameEl = document.getElementById("fullname")
const emailEl = document.getElementById("email")
const phoneEl = document.getElementById("phone")
const addressEl = document.getElementById("address")
const btnBack = document.querySelector(".btn-back")
const btnSave = document.getElementById("btn-save")
const btnSavePass = document.getElementById("btn-change-password")
const oldPassEl = document.getElementById("old-password")
const newPassEl = document.getElementById("new-password")
const errorText = document.getElementById("error")
const btnForgot = document.getElementById("btn-forgot-password")
const avatarEl = document.getElementById("avatar")
const avatarPreview = document.getElementById("avatar-preview")

const myModal = new bootstrap.Modal(document.getElementById('modal-change-password'), {
    keyboard: false
})

btnBack.addEventListener("click", function () {
    //Điều hướng trong js
    window.location.href = "/user-frontend/"
})

const getUser = async id => {
    try {
        let res = await axios.get(`${API_URL}/${id}`)
        renderUser(res.data)
    } catch (error) {
        console.log(error)
    }
}

const getCity = async () => {
    try {
        let res = await axios.get(`https://provinces.open-api.vn/api/p/`)
        renderCity(res.data)
    } catch (error) {
        console.log(error)
    }
}

const renderCity = arr => {
    let html = ""
    for (let i = 0; i < arr.length; i++) {
        const d = arr[i]
        html += `<option value="${d.name}">${d.name}</option>`
    }
    addressEl.innerHTML = html
}

//Render User 
const renderUser = user => {
    nameEl.value = user.name
    emailEl.value = user.email
    phoneEl.value = user.phone
    addressEl.value = user.address

    if(!user.avatar){
        avatarPreview.src="https://via.placeholder.com/200"
    }else{
        avatarPreview.src = `${API_URL}${user.avatar}`
    }
    
}

btnSave.addEventListener("click", async function () {
    try {
        let res = await axios.put(`${API_URL}/${id}`, {
            name: nameEl.value,
            phone: phoneEl.value,
            address: addressEl.value,
        })
        console.log(res)
        if (res.data) {
            window.location.href = "/user-frontend/"
        }
    } catch (error) {
        console.log(error)
    }

})

btnSavePass.addEventListener("click", async function () {
    try {
        await axios.put(`http://localhost:8080/user-change-password/${id}`, {
            oldPass: oldPassEl.value,
            newPass: newPassEl.value
        })
        myModal.hide()
    } catch (error) {
        errorText.innerHTML = error.response.data.message
    }
})

btnForgot.addEventListener("click", async function () {
    try {
        alert("Vui lòng kiểm tra email của bạn !")
        await axios.post(`${API_URL}/forgot/${id}`)
        
    } catch (error) {
        console.log(error)
        alert("Lấy mật khẩu mới không thành công")
    }
})

const uploadFileAPI = file =>{
    const formData = new FormData()
    formData.append("file",file)
    return axios.post(`${API_URL}/upload-file/${id}`,formData)
}
avatarEl.addEventListener("change" ,async function(event){
    let file=event.target.files[0]
try {
    let res = await uploadFileAPI(file)
    console.log(res)

    avatarPreview.src = `${API_URL}${res.data}`

    console.log(`${API_URL}${res.data}`)
} catch (error) {
    
}
    
    console.log(file)
})

const init = async () => {
    await getCity()
    await getUser(id)
}
init()


