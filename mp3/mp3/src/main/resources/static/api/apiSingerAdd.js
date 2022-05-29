const API_Singer = "http://localhost:1993/api/singer";

const avatarEl = document.getElementById("avatarEl")
const avatarPreview = document.getElementById("avatar-preview")
const nameEl = document.getElementById("nameEl")
const btnSave = document.getElementById("btn-save")

// chuyển từ file sang link
const uploadFileAPI = file => {
    const formData = new FormData();
    formData.append("file", file);
    return axios.post(`${API_Singer}/image`, formData);
}
// thêm singer nếu cập nhập ảnh
let avatarSave;
avatarEl.addEventListener("change" ,async function(event){
    let file=event.target.files[0]
try {
    let res = await uploadFileAPI(file)
    console.log(res)

    avatarPreview.src = `${API_Singer}/image/${res.data}`
    avatarSave=`uploads/${res.data}`
    console.log(avatarSave)
    console.log(avatarPreview.src)

} catch (error) {
    console.log(error)
}
btnSave.addEventListener("click", async function (event) {
    try {
        let res = await axios.post(API_Singer, {
            name: nameEl.value,
            avatar: avatarSave
        })
        console.log(res)
        if (res.data) {
            window.location.href = "/singer"
        }
    } catch (error) {
        console.log(error)
    }
})})

console.log(avatarSave)


//Thêm Singer nếu không Update ảnh
btnSave.addEventListener("click", async function (event) {

    try {
        let res = await axios.post(API_Singer, {
            name: nameEl.value,
            avatar: "images/11.jpg"
        })
        console.log(res)
        if (res.data) {
            window.location.href = "/singer"
        }
    } catch (error) {
        console.log(error)
    }

})