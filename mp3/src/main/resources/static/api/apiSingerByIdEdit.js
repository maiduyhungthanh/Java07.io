const API_SingerById = "http://localhost:1993/api/singer";
const API_Edit="http://localhost:1993/api/singer-save";
var link = location.href;
var singer;
var image;
const singerById = document.getElementById("singer-by-id")
const avatarEl = document.getElementById("avatarEl")
const avatarPreview = document.getElementById("avatar-preview")
const nameEl = document.getElementById("nameEl")
const btnSave = document.getElementById("btn-save")
//lấy id
var x = [];
for (let i = link.length - 1; i >= 0; i--) {
    if (link[i] == "C") {
        break;
    }
        x.push(link[i]);
    
}
const ID = x.reverse().join("").replace('?', '');
console.log(ID)

//Lấy dữ liệu API_SingerByID
const getSinger = async ID => {
    try {
        let res = await axios.get(`${API_SingerById}/${ID}`)
        renderSinger(res.data)
    } catch (error) {
        console.log(error)
    }
}
const uploadFileAPI = file => {
    const formData = new FormData()
    formData.append("file", file)
    return axios.post(`${API_SingerById}/files/${ID}`, formData)
}
avatarEl.addEventListener("change", async function (event) {
    let file = event.target.files[0]
    try {
        let res = await uploadFileAPI(file)
        console.log(res)
        avatarPreview.src = `${API_SingerById}/files/${ID}/${res.data}`
        console.log(`${API_SingerById}/files/${ID}/${res.data}`)
    } catch (error) {
    }
    console.log(file)
})
const renderSinger = s => {
    nameEl.placeholder = s.name,
        nameEl.value = s.name,
        avatarPreview.src = `${s.avatar}`
        let q= s.songs
        console.log(q)

btnSave.addEventListener("click", async function (event) {

        try {
            let res = await axios.post(API_Edit, {
                id: ID,
                name: nameEl.value,
                avatar: avatarPreview.src,
                songs: q
            })
            console.log(res)
            if (res.data) {
                window.location.href = "/singer"
            }
        } catch (error) {
            window.location.href = "/singer"
        }
})

}
getSinger(ID)


