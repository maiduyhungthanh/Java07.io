const API_SongById = "http://localhost:1993/api/song";
const singerById = document.getElementById("singer-by-id")
const avatarEl = document.getElementById("avatarEl")
const avatarPreview = document.getElementById("avatar-preview")
const nameEl = document.getElementById("nameEl")
const btnSave = document.getElementById("btn-save")

// lấy id
var link = location.href;
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
const getSong = async ID => {
    try {
        let res = await axios.get(`http://localhost:1993/api/song/${ID}`)
        renderUser(res.data)
    } catch (error) {
        console.log(error)
    }
}
const renderUser = s => {
    nameEl.placeholder = s.name,
        nameEl.value = s.name,
        avatarPreview.src = `${s.avatar}`
      
}
getSong(ID)