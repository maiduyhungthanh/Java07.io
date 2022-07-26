
let list_album = [];
const users = document.getElementById("user-table")
const role = document.getElementById("role")

console.log(role[0].value)
const getListAlbum = async () => {
  let res = await axios.get("http://localhost:1993/api/users");
  list_user = res.data;
  console.log(list_user);
  let html = ``
  let select = ``
  for (let i = 0; i < list_user.length; i++) {
    let u = list_user[i];
    if(u.roles.length==3){
        select = `    <option value="admin">ROLE_ADMIN</option>
        <option value="operator">ROLE_OPERATOR</option>
        <option value="user">ROLE_USER</option>`
    }
    if(u.roles.length==2){
        select=`
        <option value="operator">ROLE_OPERATOR</option>
        <option value="admin">ROLE_ADMIN</option>
        <option value="user">ROLE_USER</option>`
    }
    if(u.roles.length==1){
        select=`
        <option value="user">ROLE_USER</option>
        <option value="admin">ROLE_ADMIN</option>
        <option value="operator">ROLE_OPERATOR</option>
        `
    }
    html += `
    <tr>
    <td>
        <label class="users-table__checkbox">
            <div class="categories-table-img">
                <a href=""><img src="${u.avatar}" alt=""></a>
            </div>
        </label>
    </td>
    <td>
        ${u.email}
    </td>
    <td><span class="badge-pending">${u.state}</span></td>
    <td>
    <select class="badge-pending" style="width:132px;" id="role">
      ${select}
    </select>
    </td>
    <td id="href"><a href="http://localhost:1993/api/users/${u.id}"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" color="yellow" class="bi bi-wrench-adjustable" viewBox="0 0 16 16">
    <path d="M16 4.5a4.492 4.492 0 0 1-1.703 3.526L13 5l2.959-1.11c.027.2.041.403.041.61Z"/>
    <path d="M11.5 9c.653 0 1.273-.139 1.833-.39L12 5.5 11 3l3.826-1.53A4.5 4.5 0 0 0 7.29 6.092l-6.116 5.096a2.583 2.583 0 1 0 3.638 3.638L9.908 8.71A4.49 4.49 0 0 0 11.5 9Zm-1.292-4.361-.596.893.809-.27a.25.25 0 0 1 .287.377l-.596.893.809-.27.158.475-1.5.5a.25.25 0 0 1-.287-.376l.596-.893-.809.27a.25.25 0 0 1-.287-.377l.596-.893-.809.27-.158-.475 1.5-.5a.25.25 0 0 1 .287.376ZM3 14a1 1 0 1 1 0-2 1 1 0 0 1 0 2Z"/>
  </svg></a></td>
    </tr>
            `
  }
  users.innerHTML = html;
  role.addEventListener("change",function() {
    
  })
}

getListAlbum();
