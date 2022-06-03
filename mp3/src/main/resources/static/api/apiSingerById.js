const API_SingerById = "http://localhost:1993/api/singer";
var link = location.href;
console.log(link)

var singer;
var ca_sy;
const singerById = document.getElementById("singer-by-id")
const btnDel = document.getElementById("delete")
const nameEl = document.getElementById("nameEl")
const imgEl = document.getElementById("imgEl")
const editEl = document.getElementById("edit")
const list_song = document.getElementById("list-song")


console.log(nameEl.innerHTML)
//lấy id
var x = [];
for (let i = link.length - 1; i >= 0; i--) {
    if (link[i] == "C") {
        break;
    }
    x.push(link[i]);
}
const id = x.reverse().join("").replace('?', '');
console.log(id)

//Lấy dữ liệu API
const getListSinger = async () => {
    let res = await axios.get(`${API_SingerById}/${id}`);
    singer = res.data;
    console.log(singer.name);
    editEl.innerHTML = `<a href="/singer-edit|${singer.id}">
    <p style="color: blueviolet;margin-right: -400px;font-size: 20px;">Chỉnh Sửa</p>
  </a>`
    console.log(editEl.innerHTML)
    nameEl.innerHTML = `Ca Sỹ : ${singer.name}`;
    imgEl.src = `${singer.avatar}`;
    list_song.innerHTML = `Danh sách bài hát của ${singer.name}`;
}


const API_Song = "http://localhost:1993/api/song";
const songAll = document.getElementById("song-table");

let myPlayList = [];
//lấy API Singer
const getListSong = async () => {
    let res = await axios.get(`${API_SingerById}/song/${id}`);
    myPlayList = res.data;
    console.log(myPlayList);
    let html = "";
    for (let i = 0; i < myPlayList.length; i++) {
        let singer_name = []
        let singerId = []
        let s = myPlayList[i];
        let x = Array.from(myPlayList[i].singers);
        for (let j = 0; j < x.length; j++) {
            singer_name.push(x[j].name)
            singerId.push(x[j].id)
        }
    
            html += `
		</tr>
		<tr>
		  <td>
			<label class="users-table__checkbox">
			  <div class="categories-table-img">
			  <a href="/song|${s.id}"><img src="${s.avatar}" alt=""></a>
			  </div>
			</label>
		  </td>
		  <td>
			${s.name}
		  </td>
		  <td>
			<div class="pages-table-img">
			  ${singer_name.join(" & ")}
			</div>
		  </td>
		  <td><span class="badge-pending">1.000.000 view</span></td>
		</tr>
		`
        singer_name.splice(0, singer_name.length);
    }
    songAll.innerHTML = html
}
console.log(`${API_SingerById}-delete/${id}`)
btnDel.addEventListener("click", async function () {

    let res = await axios.delete(`${API_SingerById}/delete/${id}`)
    console.log(res)
    window.location.href = "/singer"
})
getListSong();
getListSinger();
