<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <style>
        html,
        body,
        #google-map {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0
        }
        #search-panel {
            position: absolute;
            top: 10px;
            left: 25%;
            z-index: 5;
            background-color: #FFFFFF;
            padding: 5px;
            border: 1px solid black;
            text-align: center;
            padding: left: 10px
        }
    </style>
    <title></title>
</head>
<body>
    <div id="search-panel">
        <input id="address" type="text" value="" />
        <button id="submit" type="button" value="Geocode">지도 검색</button>
        <form method="post" action="mama.do">
        	<input type="text" name="lat" id="lat">
        	<input type="text" name="lng" id="lng">
        	<input type="text" name="location" id="location">
        	<input type="submit" value="db저장!">
        </form>
    </div>
    <div id="google-map">
    </div>
 
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <!-- Google Map API -->
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2O-wjFf77N1RbEEuWc0WdZ3FN_Kp95w4&callback=initMap">
    </script>
    <script>
        /**
         * Google Map API 주소의 callback 파라미터와 동일한 이름의 함수이다.
         * Google Map API에서 콜백으로 실행시킨다.
         */
        function initMap() {
            console.log('Map is initialized.');
 
            /**
             * 맵을 설정한다.
             * 1번째 파라미터 : 구글 맵을 표시할 위치. 여기서는 #google-map
             * 2번째 파라미터 : 맵 옵션.
             *      ㄴ zoom : 맵 확대 정도
             *      ㄴ center : 맵 중심 좌표 설정
             *              ㄴ lat : 위도 (latitude)
             *              ㄴ lng : 경도 (longitude)
             */
            var map = new google.maps.Map(document.getElementById('google-map'), {
                zoom: 2,
                center: {
                    lat: -34.397,
                    lng: 150.644
                }
            });
 
            /**
             * Google Geocoding. Google Map API에 포함되어 있다.
             */
            var geocoder = new google.maps.Geocoder();
 
            // submit 버튼 클릭 이벤트 실행
            document.getElementById('submit').addEventListener('click', function() {
                console.log('submit 버튼 클릭 이벤트 실행');
 
                // 여기서 실행
                geocodeAddress(geocoder, map);
            });
 
            /**
             * geocodeAddress
             * 
             * 입력한 주소로 맵의 좌표를 바꾼다.
             */
            function geocodeAddress(geocoder, resultMap) {
                console.log('geocodeAddress 함수 실행');
 
                // 주소 설정
                var address = document.getElementById('address').value;
                console.log(address);
                /**
                 * 입력받은 주소로 좌표에 맵 마커를 찍는다.
                 * 1번째 파라미터 : 주소 등 여러가지. 
                 *      ㄴ 참고 : https://developers.google.com/maps/documentation/javascript/geocoding#GeocodingRequests
                 * 
                 * 2번째 파라미터의 함수
                 *      ㄴ result : 결과값
                 *      ㄴ status : 상태. OK가 나오면 정상.
                 */
                geocoder.geocode({'address': address}, function(result, status) {
                    console.log(result);
                    console.log(status);
 
                    if (status === 'OK') {
                        // 맵의 중심 좌표를 설정한다.
                        resultMap.setCenter(result[0].geometry.location);
                        // 맵의 확대 정도를 설정한다.
                        resultMap.setZoom(18);
                        // console.log("주소 나오나? "+result[0].geometry); .location 까지 붙이면 위경도 나옴
                        
                        // 맵 마커
                        var marker = new google.maps.Marker({
                            map: resultMap,
                            position: result[0].geometry.location
                        });
                        //console.log("위도가 숫자가 아니면 "+isNaN(marker.position.lat())); 위경도는 숫자
                        // 위도
                        console.log('위도(latitude) : ' + marker.position.lat());
                        // 경도
                        console.log('경도(longitude) : ' + marker.position.lng());
                        
                        $("#lat").val(marker.position.lat());
                        $("#lng").val(marker.position.lng());
                        $("#location").val(address);
                        
                    } else {
                        alert('지오코드가 다음의 이유로 성공하지 못했습니다 : ' + status);
                    }
                    
                    
                });
            }
        }
        
        
    </script>
    
</body>
</html>