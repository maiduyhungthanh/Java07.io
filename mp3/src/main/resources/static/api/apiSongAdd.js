const singerEl = document.querySelectorAll(".singerEl")
const avatarPreview = document.getElementById("avatar-preview")
const avatarEl = document.getElementById("avatarEl")
const mp3El = document.getElementById("mp3El")
const nameEl = document.getElementById("nameEl")
const btnSave = document.getElementById("btn-save")
const singer1 = document.getElementById("singer1")
const singer2 = document.getElementById("singer2")
const singer3 = document.getElementById("singer3")
const link_mp3 = document.getElementById("linkmp3")
const id_singer = document.getElementById("id_singer")
var listSinger = []

const getSinger = async () => {
    try {
        let res = await axios.get("http://localhost:1993/api/singer")
        console.log(res.data);

        renderSinger(res.data)
    } catch (error) {
        console.log(error)
    }
}// chuyển từ mp3 ảnh sang link
const mp3API = file => {
    const formData = new FormData();
    formData.append("file", file);
    return axios.post(`http://localhost:1993/api/song/mp3`, formData);
}
// thêm singer nếu cập nhập mp3
mp3El.addEventListener("change", async function (event) {
    let r= await axios.get("http://localhost:1993/api/singer")
    console.log(r.data)
    for (let i = 0; i < r.data.length; i++) {
        if(singerEl[0].value == r.data[i].id||
            singerEl[1].value == r.data[i].id||
            singerEl[2].value == r.data[i].id){
            listSinger.push(r.data[i].id)
        }
    }
    console.log(listSinger)
    id_singer.innerHTML+= listSinger.join("/")
    let file = event.target.files[0]
    try {
        let res = await mp3API(file)
        console.log(res)
        link_mp3.innerHTML = `http://localhost:1993/api/song/mp3/${res.data}`
        console.log(link_mp3.innerHTML)

    } catch (error) {
        console.log(error)
    }
})
// chuyển từ file ảnh sang link
const uploadFileAPI = file => {
    const formData = new FormData();
    formData.append("file", file);
    return axios.post(`http://localhost:1993/api/song/image`, formData);
}
// thêm singer nếu cập nhập ảnh
avatarEl.addEventListener("change", async function (event) {
    let file = event.target.files[0]
    try {
        let res = await uploadFileAPI(file)
        console.log(res)
        avatarPreview.src = `http://localhost:1993/api/song/image/${res.data}`
        console.log(avatarPreview.src)

    } catch (error) {
        console.log(error)
    }
})
const renderSinger = arr => {
    let html = `<option value="123"  selected="selected">Không</option>`
    // let html =``
    for (let i = 0; i < arr.length; i++) {
        const d = arr[i]
        html += `<option value="${d.id}">${d.name}</option>`
    }
    for (let j = 0; j < singerEl.length; j++) {
        const s = singerEl[j]
        s.innerHTML = html;
    }
}

//Thêm Singer nếu không Update ảnh
btnSave.addEventListener("click", async function () {

    try {
        let res = await axios.post(`http://localhost:1993/api/song`,
            {
                    name: nameEl.value,
                    avatar: avatarPreview.src,
                    mp3: link_mp3.innerHTML,
                    lyric:id_singer.innerHTML
                
            })
        if (res.data) {
            window.location.href = "/"
        }
    } catch (error) {
        window.location.href = "/singer"
    }

})

const init = async () => {
    await getSinger()
}
init()
