const API_SongById = "http://localhost:1993/api/song";
const singerById = document.getElementById("singer-by-id")
const avatarEl = document.getElementById("avatarEl")
const avatarPreview = document.getElementById("avatar-preview")
const nameEl = document.getElementById("nameEl")
const singerEl = document.querySelectorAll(".singerEl")
const btnSave = document.getElementById("btn-save")
console.log(singerEl.length)

// lấy id Song
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

//Lấy dữ liệu Song truyền vào view
const getSong = async ID => {
    try {
        let res = await axios.get(`http://localhost:1993/api/song/${ID}`)
        renderSong(res.data);

    } catch (error) {
        console.log(error)
    }
}
    // thay đổi ảnh Song
    const uploadFileAPI = file => {
        const formData = new FormData()
        formData.append("file", file)
        return axios.post(`http://localhost:1993/api/song/files/${ID}`, formData)
    }
    avatarEl.addEventListener("change", async function (event) {
        let file = event.target.files[0]
        try {
            let res = await uploadFileAPI(file)
            avatarPreview.src = `http://localhost:1993/api/song/files/${ID}/${res.data}`;
            console.log(`http://localhost:1993/api/song/files/${ID}/${res.data}`)
        } catch (error) {
        }
        console.log(file)
    })

// lam select Singer
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
        const s = singerEl[j]
        s.innerHTML = html;
    }
}

let count = 0;
const renderSong = s => {
    nameEl.value = s.name,
        avatarPreview.src = `${s.avatar}`,

        s.singers.forEach(s => {
            singerEl[count].value = s.name
            count++;
        });

    btnSave.addEventListener("click", async function () {
        try {
            let res = await axios.post("http://localhost:1993/api/song-save", {
                id: ID,
                name: nameEl.value,
                avatar: avatarPreview.src
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
const init = async () => {
    await getSinger()
    getSong(ID)
}
init()
