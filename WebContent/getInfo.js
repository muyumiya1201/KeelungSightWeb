/**
 * Description: get and show the info got from json
 */

function getInfo(event) { // 拿到json並顯示出來
  const zone = event.target.value; // 拿到button的value
  const result = document.getElementById('showData'); // 拿到showData這塊
  // 拿到json，下面是要get json資料的網址，注意要用 " ` "
  //手機網路: 192.168.43.202
  //家裡wifi: 192.168.1.104
  fetch(`http://192.168.2.104:8080/SightAPI.do/?zone=${zone}`, {
    AccessControlAllowMethods: '*',
    method: 'GET', // 從doGet拿，因為東西寫在doGet裡
  }).then(resp => resp.json()) // 把拿到的resp轉成json
    .then((text) => { // 開始印東西，這裡的text就是我們hw2看到的json的樣子
      result.innerHTML = ''; // 每重按一次button要清空頁面再顯示出新的東西
      for (let i = 0; i < text.length; i += 1) { // 印出每個景點，所以跑text的長度
        // 先把要寫的東西寫好，再印出來
        // 這樣就不用創好幾個id
        // out為card的樣板
    	if(text[i].description == "")
    	{
    		text[i].description = "no description"
    	}
        let out = `<div class="card">
        
                      <div class="card-body">
                        <h5 class="card-title"><b>${text[i].sightName}</b></h5>
                        <p class="card-text">區域: ${text[i].zone}</p>
                        <p class="card-text">分類: ${text[i].category}</p>
                        <button class="btn btn-info">
                          <a href ="https://www.google.com/maps?q=${text[i].address}">地址</a>
                        </button>
                        <a class="btn btn-primary" data-toggle="collapse"
                        href="#collapseExample${i}" role="button" aria-expanded="false"
                        aria-controls="collapseExample"> 詳細資訊 </a>
                      </div>
                      
                      <div class="collapse" id="collapseExample${i}">
                        <div class="card card-body">`;
        	if(text[i].photoURL != ""){
        		out += `<p class="card-image">圖片:<br><img src=${text[i].photoURL}></p>`;
        	}
        	else {
        		out += `<p class="card-text">圖片:<br>no picture</p>`;
			}
    			out += `<p class="card-text" style="font-size:100%;">描述:<br>${text[i].description}</p>
                      </div>
                    </div>
                  </div>`;
        // 地址的部分要再改
        result.innerHTML += out; // 印出來，要用+=因為要疊加
      }
      result.innerHTML += '<div style="clear:both;"></div>';
    });
}

// 根據按下的zone id來判斷是哪個區域
function findTheButton() {
  for (let i = 1; i <= 7; i += 1) {
    const id = `zone${i}`; // 判斷id
    document.getElementById(id).onclick = getInfo; // 如果按下按鈕了，就呼叫getInfo
  }
}

window.onload = findTheButton;
