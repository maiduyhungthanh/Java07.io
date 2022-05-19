const API_URL = "http://localhost:1993/api";
let list_song = [];
let songs = [];
let searchEl = document.getElementById('search');
// searchEl.style.marginLeft = '1000px';
console.log("xxxxx");
//APi lấy danh sách user kiêm tìm kiếm ListSong
const getListSongAPI = (keyword = "") => {
    let url = `http://localhost:1993`;
    console.log(url);
    if (keyword != "") {
        url = `${API_URL}/search?search=${keyword}`
    }
    return axios.get(url);

}
//..........................................

const getListSong = async (keyword = "") => {
    try {
        let res = await getListSongAPI(keyword)
        list_song = res.data;
        console.log(list_song.length)
        // Tiêu đề bài hát, tương ứng với các file mp3
        for (let i = 0; i < list_song.length; i++) {
            songs.push(list_song[i].name)

        }
        console.log(songs[0].name);
    } catch (e) {
        console.log(e)
    }

}


getListSong();