const API_Singer = "http://localhost:1993/api/singer";

const avatarEl = document.getElementById("avatarEl")
const avatarPreview = document.getElementById("avatar-preview")
const nameEl = document.getElementById("nameEl")
const btnSave = document.getElementById("btn-save")


//Thêm Singer
btnSave.addEventListener("click", async function () {
    try {
        let res = await axios.post(API_Singer, {
            name: nameEl.value,
            // avatar:avatarPreview.value
        })
        console.log(res)
        if (res.data) {
            window.location.href = "/singer"
        }
    } catch (error) {
        console.log(error)
    }

})