const API_Singer = "http://localhost:1993/api/category";

const nameEl = document.getElementById("nameEl")
const btnSave = document.getElementById("btn-save")


//Thêm Singer nếu không Update ảnh
btnSave.addEventListener("click", async function (event) {

    try {
        let res = await axios.post(API_Singer, {
            name: nameEl.value
        })
        if (res.data) {
            window.location.href = "/category"
        }
    } catch (error) {
        alert(lỗi);
    }

})

