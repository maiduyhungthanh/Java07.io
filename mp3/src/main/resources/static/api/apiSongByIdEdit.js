const API_SongById = "http://localhost:1993/api/song";
const singerById = document.getElementById("singer-by-id")
const avatarEl = document.getElementById("avatarEl")
const avatarPreview = document.getElementById("avatar-preview")
const nameEl = document.getElementById("nameEl")
const singerEl = document.querySelectorAll(".singerEl")
const btnSave = document.getElementById("btn-save")
console.log(singerEl.length)

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
        renderUser(res.data);

    } catch (error) {
        console.log(error)
    }
}
let count =0;
const renderUser = s => {
    nameEl.value = s.name,
        avatarPreview.src = `${s.avatar}`,
        
        s.singers.forEach(s => {
            singerEl[count].value=s.name
            count ++;
        });
}

const getSinger = async () => {
    try {
        let res = await axios.get("http://localhost:1993/api/singer")
        console.log(res.data);

        renderSinger(res.data)
    } catch (error) {
        console.log(error)
    }
}

const renderSinger = arr => {
    let html = `<option value="">Không</option>`
    for (let i = 0; i < arr.length; i++) {
        const d = arr[i]
        html += `<option value="${d.name}">${d.name}</option>`
    }
    for (let j = 0; j < singerEl.length; j++) {
        const s= singerEl[j]
        s.innerHTML=html;
}    }
const init = async () => {
    await getSinger()
    getSong(ID)
}
init()
