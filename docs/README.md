### 🖥️와이어 프레임


### 📋ERD
<img src="images/erd.png"/>

### 📑API 명세서
<details><summary><b>API 명세서(펼치기/접기)</b></summary>

<table>
    <tr>
        <th>api&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
        <th>Method</th>
        <th>URL</th>
        <th>request header</th>
        <th>request</th>
        <th>response header</th>
        <th>response</th>                               
<th>status&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </th>
    </tr>
    <tr>
        <td><b>회원 가입</b></td>
        <td><span style=background-color:#786E12AA;font-weight:bold;>POST</span></td>
        <td><span>/api/auth/signup</span></td>
        <td><code>N/A</Code></td>
        <td><pre lang="json">{
    "email": String,
    "password": String,
    "nickname": String,
    "birth": "2000-01-01"
}</pre></td>
        <td>201</td>
        <td><code>N/A</Code></td>
        <td>
            <span style=background-color:yellow;font-weight:bold;color:black;>201</span>: 성공<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>400</span>: 잘못된 요청<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>401</span>: 인증 실패
        </td>
    </tr>
    <tr>
        <td><b>로그인</b></td>
        <td><span style=background-color:#786E12AA;font-weight:bold;>POST</span></td>
        <td><span>/api/auth/signin</span></td>
        <td><code>N/A</Code></td>
        <td><pre lang="json">{
    "email": String,
    "password": String
}</pre></td>
        <td>
            <span>200</span></br>
            <span>JWT</span>
        </td>
        <td><pre lang="json">{
    "message": "login success",
    "status": 201
}</pre></td>
        <td>
            <span style=background-color:yellow;font-weight:bold;color:black;>200</span>: 성공<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>400</span>: 잘못된 요청<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>401</span>: 인증 실패
        </td>
    </tr>
    <tr>
        <td><b>프로필<br/>조회</b></td>
        <td><span style=background-color:#22741CAA;font-weight:bold;>GET</span></td>
        <td><span>/api/profile</span></td>
        <td>Authorization</td>
        <td><code>N/A</code></td>
        <td>200</td>
        <td><pre lang="json">{
    "id": 1,
    "nickname": "닉네임",
    "birth": "2000-01-01",
    "createdAt": "2024-11-22 00:00:00",
    "modifedtAt": "2024-11-22 00:00:00"
}</pre></td>
        <td>
            <span style=background-color:yellow;font-weight:bold;color:black;>200</span>: 성공<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>400</span>: 토큰이 없음<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>401</span>: 인증 실패
        </td>
    </tr>
    <tr>
        <td><b>프로필<br/>수정</b></td>
        <td><span style=background-color:#3B36CFAA;font-weight:bold;>PUT</span></td>
        <td>/api/profile</td>
        <td>Authorization</td>
        <td><pre lang="json">{
    "password": String,
    "nickname": String,
    "birth": "2000-01-01"
}</pre></td>
        <td>200</td>
        <td><pre lang="json">{
    "id": 1,
    "nickname": "닉네임",
    "birth": "2000-01-01",
    "createdAt": "2024-11-22 00:00:00",
    "modifedtAt": "2024-11-22 00:00:00"
}</pre></td>
        <td>
            <span style=background-color:yellow;font-weight:bold;color:black;>200</span>: 성공<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>400</span>: 토큰이 없음<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>401</span>: 인증 실패
        </td> 
    </tr>
    <tr>
        <td><b>회원 탈퇴</b></td>
        <td><span style=background-color:#CE3636AA;font-weight:bold;>DELETE</span></td>
        <td>/api/profile</td>
        <td>Authorization</td>
        <td><pre lang="json">{
    "password": String
}</pre></td>
        <td>204</td>
        <td><code>N/A</Code></td>
        <td>
            <span style=background-color:yellow;font-weight:bold;color:black;>204</span>: 성공<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>400</span>: 토큰이 없음<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>401</span>: 인증 실패
        </td> 
    </tr>
    <tr>
        <td><b>좋아요<br/>추가</b></td>
        <td><span style=background-color:#786E12AA;font-weight:bold;>POST</span></td>
        <td><span>/api/likes<br/>/{productId}</span></td>
        <td>Authorization</td>
        <td><code>N/A</Code></td>
        <td>201</td>
        <td><code>N/A</Code></td>
        <td>
            <span style=background-color:yellow;font-weight:bold;color:black;>201</span>: 성공<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>400</span>: 토큰이 없음<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>401</span>: 인증 실패
        </td> 
    </tr>
    <tr>
        <td><b>좋아요<br/>취소</b></td>
        <td><span style=background-color:#CE3636AA;font-weight:bold;>DELETE</span></td>
        <td><span>/api/likes<br/>/{productId}</span></td>
        <td>Authorization</td>
        <td><code>N/A</Code></td>
        <td>204</td>
        <td><code>N/A</Code></td>
        <td>
            <span style=background-color:yellow;font-weight:bold;color:black;>204</span>: 성공<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>400</span>: 토큰이 없음<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>401</span>: 인증 실패
        </td> 
    </tr>    
    <tr>
        <td><b>단일 상품<br/>조회</b></td>
        <td><span style=background-color:#22741CAA;font-weight:bold;>GET</span></td>
        <td><span>/api/products<br/>/{productId}</span></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td>200</td>
        <td><pre lang="json">{
    "id": 1,
    "name": "고구마",
    "price": "50000",
    "likes": int,
    "avgScore": 4.8
}</pre></td>
        <td>
            <span style=background-color:yellow;font-weight:bold;color:black;>200</span>: 성공
        </td>
    </tr>    
    <tr>
        <td><b>상품 검색</b></td>
        <td><span style=background-color:#22741CAA;font-weight:bold;>GET</span></td>
        <td><span>/api/v1/products?<br/>keyword={keyword}<br/>&minPrice={minPrice}<br/>&isTrend={isTrend}<br/>&page={page}<br/>&size={size}</span></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td>200</td>
        <td><pre lang="json">{
    "data": 
    [
      {
        "id": 1,
        "name": "고구마",
        "price": "50000",
        “likes”: 333,
        “avgScore”: 4.8
      }
    ],  
    "page": 1,
    "size": 10,
    "totalPage": 1
}</pre></td>
        <td>
            <span style=background-color:yellow;font-weight:bold;color:black;>200</span>: 성공
        </td>
    </tr>
    <tr>
        <td><b>인기<br/>검색어</b></td>
        <td><span style=background-color:#22741CAA;font-weight:bold;>GET</span></td>
        <td><span>/api/trend</span></td>
        <td><code>N/A</code></td>
        <td><code>N/A</code></td>
        <td>200</td>
        <td><pre lang="json">{
    "data": 
    [
      {
        "id": 1,
        "keyword": "고구마"
      }
    ]  
}</pre></td>
        <td>
            <span style=background-color:yellow;font-weight:bold;color:black;>200</span>: 성공
        </td>
    </tr>
    <tr>
        <td><b>리뷰 등록</b></td>
        <td><span style=background-color:#786E12AA;font-weight:bold;>POST</span></td>
        <td><span>/api/products<br/>/{productId}/reviews</span></td>
        <td>Authorization</td>
        <td><pre lang="json">{
    "id": Long,
    "star": 5,
    "comment": "너무 맛있어요!"
}</pre></td>
        <td>201</td>
        <td><pre lang="json">{
    "id": 1,
    "star": 5,
    "comment": "너무 맛있어요!",
    "nickname": “과일 공주",
    "createdAt": "2024-11-22"
}</pre></td>
        <td>
            <span style=background-color:yellow;font-weight:bold;color:black;>201</span>: 성공<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>400</span>: 토큰이 없음<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>401</span>: 인증 실패
        </td>   
    </tr>
        <tr>
        <td><b>리뷰 보기</b></td>
        <td><span style=background-color:#22741CAA;font-weight:bold;>GET</span></td>
        <td><span>/api/products<br/>/{productId}/reviews<br/>?page={page}<br/>&size={size}</span></td>
        <td><code>N/A</Code></td>
        <td><code>N/A</code></td>
        <td>200</td>
        <td><pre lang="json">{
    "data": [
    {
      "id": 1,
      "star": 5,
      "comment": "너무 맛있어요!",
      "nickname": "귤쟁이",
      "createdAt": "2024-11-22 00:00:00",
      "modifiedAt": "2024-11-22 00:00:00"
    },
    {
      "id": 2,
      "star": 4,
      "comment": "맛있어요!",
      "nickname": "농산물 킬러",
      "createdAt": "2024-11-22 00:00:00",
      "modifiedAt": "2024-11-22 00:00:00"
     }
    ],
    "page": 1,
    "size": 10,
    "totalPage": 1
}</pre></td>
        <td>
            <span style=background-color:yellow;font-weight:bold;color:black;>200</span>: 성공
        </td>   
    </tr>
    <tr>
        <td><b>리뷰 수정</b></td>
        <td><span style=background-color:#3B36CFAA;font-weight:bold;>PUT</span></td>
        <td><span>/api/products<br/>/{productId}/reviews<br/>/{reviewId}</span></td>
        <td>Authorization</td>
        <td><pre lang="json">{
    "id": Long.
    "star": 4,
    "comment": "맛있어요!"
}</pre></td>
        <td>200</td>
        <td><pre lang="json">{
    "id": 1,
    "star": 4,
    "comment": "맛있어요!",
    "nickname": “과일 공주",
    "modifiedAt" : "2024-11-22"
}</pre></td>
        <td>
            <span style=background-color:yellow;font-weight:bold;color:black;>200</span>: 성공<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>400</span>: 토큰이 없음<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>401</span>: 인증 실패
        </td> 
    </tr>
    <tr>
        <td><b>리뷰 삭제</b></td>
        <td><span style=background-color:#CE3636AA;font-weight:bold;>DELETE</span></td>
        <td><span>/api/products<br/>/{productId}/reviews<br/>/{reviewId}</span></td>
        <td>Authorization</td>
        <td><pre lang="json">{
    "id": Long
}</pre></td>
        <td>204</td>
        <td><code>N/A</Code></td>
        <td>
            <span style=background-color:yellow;font-weight:bold;color:black;>200</span>: 성공<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>400</span>: 토큰이 없음<br/>
            <span style=background-color:skyblue;font-weight:bold;color:black;>401</span>: 인증 실패
        </td> 
    </tr>          
</table>
</details>
