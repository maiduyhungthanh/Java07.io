const API_URL = "http://localhost:8080/users"
let users = []
let searchEL = document.getElementById("search")

//APi lấy danh sách user kiêm tìm kiếm Users
const getUsersAPI = (keyword = "") => {
    let url = API_URL
    console.log(url);
    if (keyword != "") {
        url = `${API_URL}/search?name=${keyword}`
    }
    return axios.get(url)
}

const getUsers = async (keyword) => {
    try {
        let res = await getUsersAPI(keyword)
        console.log(res)
        users = res.data
        renderUsers(users)
        console.log(users)
    } catch (e) {
        console.log(e)
    }
}

//Hien thi user
const tableContentEL = document.querySelector("tbody")
const renderUsers = arr => {
    tableContentEL.innerHTML = "";
    let html = "";
    for (let i = 0; i < arr.length; i++) {
        const u = arr[i]
        html += `
        <tr>
            <td>${i + 1}</td>
            <td>${u.name}</td>
            <td>${u.email}</td>
            <td>${u.phone}</td>
            <td>${u.address}</td>
            <td>
            <a href="./detail.html?id=${u.id}" class="btn btn-success">Xem chi tiết</a>
            <button class="btn btn-danger" onclick="deleteUser(${u.id})">Xóa</button>
            </td>
        </tr>`
    }

    tableContentEL.innerHTML = html
}

searchEL.addEventListener("keydown", function (event) {
    if (event.keyCode == 13) {
        let value = event.target.value
        getUsers(value)
    }
})
//Xoa User
async function deleteUser(id) {
    try {
        //Gọi API xóa
        let isDelete = confirm("Bạn muốn xóa không ?")
        if (isDelete) {
            axios.delete(`${API_URL}/${id}`)
            users.forEach((user, index) => {
                if (user.id == id) {
                    users.splice(index, 1)
                }
            })
            renderUsers(users)
        }


    } catch (error) {
        console.log(error)
    }
}
getUsers()