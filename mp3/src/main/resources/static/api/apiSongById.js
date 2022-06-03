const API_Song = "http://localhost:1993/api/song";
const songAll = document.getElementById("song-table");
const edit = document.getElementById("edit");

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

let myPlayList = [];
//lấy API Singer
const getListSong = async () => {
	let res = await axios.get(`http://localhost:1993/api/song/${ID}`);
	myPlayList = res.data;
	console.log(myPlayList);
	let singer = []
	let singerId=[]
	let html = "";
    edit.innerHTML=`<a href="http://localhost:1993/song-edit|${ID}" >Chỉnh Sửa Thông Tin</a>`
	// for (let i = 0; i < myPlayList.length; i++) {
	// 	let s = myPlayList[i];
		let x = Array.from(myPlayList.singers);
		for (let j = 0; j < x.length; j++) {
			singer.push(x[j].name)
			singerId.push(x[j].id)
		}
		console.log(singer)
		html += `
		</tr>
		<tr>
		  <td>
			<label class="users-table__checkbox">
			  <div class="categories-table-img">
			  <a href="/song|${myPlayList.id}"><img src="../${myPlayList.avatar}" alt=""></a>
			  </div>
			</label>
		  </td>
		  <td>
			${myPlayList.name}
		  </td>
		  <td>
			<div class="pages-table-img">
			  ${singer.join(" & ")}
			</div>
		  </td>
		  <td><span class="badge-pending">1.000.000 view</span></td>
		</tr>
		`
	// 	singer.splice(0, singer.length);
	// }
	songAll.innerHTML = html
	// $(function () {
	// 	var playItem = 0,
	// 		title = $('.jp-interface .jp-title'),
	// 		jPlayer = $("#jplayer"),
	// 		myPlayList = res.data,
	// 		jPlay = function (idx) {
	// 			if (typeof idx == typeof 0)
	// 				jPlayer.jPlayer("setMedia", myPlayList[idx]).jPlayer('play')
	// 			if (typeof idx == typeof '')
	// 				jPlayer.jPlayer("setMedia", myPlayList[playItem = idx == 'next' ? (++playItem < myPlayList.length ? playItem : 0) : (--playItem >= 0 ? playItem : myPlayList.length - 1)]).jPlayer('play')
	// 			title.text(myPlayList[playItem].name)
	// 			Cufon.refresh()
	// 		}

	// 	jPlayer.jPlayer({
	// 		ready: function () {
	// 			jPlay(playItem)
	// 		},
	// 		ended: function () {
	// 			jPlay('next')
	// 		}
	// 	})

	// 	$(".jp-prev,.jp-next")
	// 		.click(function () {
	// 			jPlay($(this).is('.jp-next') ? 'next' : 'prev')
	// 			return false;
	// 		})

	// });
}
getListSong();
