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
avatarEl.addEventListener("change", async function (event) {
    let file = event.target.files[0]
    try {
        let res = await uploadFileAPI(file)
        console.log(res)

        avatarPreview.src = `${API_Singer}/image/${res.data}`
        console.log(avatarPreview.src)

    } catch (error) {
        console.log(error)
    }
})

console.log(avatarPreview.src)

//Thêm Singer nếu không Update ảnh
btnSave.addEventListener("click", async function (event) {

    try {
        let res = await axios.post(API_Singer, {
            name: nameEl.value,
            avatar: avatarPreview.src
        })
        if (res.data) {
            window.location.href = "/singer"
        }
    } catch (error) {
        console.log(error)
    }

})

