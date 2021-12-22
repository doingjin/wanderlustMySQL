<%@ tag language="java" pageEncoding="UTF-8"%>

	<a href="#" class="js-fh5co-nav-toggle fh5co-nav-toggle"><i></i></a>
	<aside id="fh5co-aside" role="complementary"
		class="border js-fullheight">

		<h1 id="fh5co-logo">
			<img src="${mem.profile}" class="gg"
				alt="profile">
		</h1>
		<h3 align="center"><a href="mypage.jsp" title="go to mypage!">${mem.mname}</a></h3>
		<nav id="fh5co-main-menu" role="navigation">
			<ul>
				<li class="fh5co-active"><a href="main.do">Log</a></li>
				<li><a href="insert.jsp">Add Post</a></li>
				<li><a href="mypage.jsp">My Page</a></li>
				<li><a href="mmap.do">Map</a></li>
				<li><a onclick="logout()" style="cursor: pointer;">logout</a></li>
			</ul>
		</nav>

		<div class="fh5co-footer">
			<p>
				<small>&copy; 2021 wanderlust <span>Designed
						by <a href="http://freehtml5.co/" target="_blank">FreeHTML5.co</a>
				</span> <span>Distributed by: <a href="https://themewagon.com/"
						target="_blank">ThemeWagon</a></span></small>
			</p>
			<ul>
				<li><a href="https://www.google.com" target="_blank"><i class="icon-google"></i></a></li>
				<li><a href="https://github.com/doingjin" target="_blank"><i class="icon-github"></i></a></li>
				<li><a href="https://www.instagram.com" target="_blank"><i class="icon-instagram"></i></a></li>
				<li><a href="https://blog.naver.com/jogjin37" target="_blank"><i class="icon-flag-checkered"></i></a></li>
			</ul>
		</div>

	</aside>