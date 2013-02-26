<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %>
<c:set var="ImgUrl" value="${c:encodeURL('/images')}" scope="request" />
.main {
  padding: 15px;
}
.z-groupbox-3d .open-false,
.z-groupbox-3d .open-true {
  background: url("${ImgUrl}/arrow.png") no-repeat right 0;
  height: 16px; 
  padding-right: 20px;
  font-weight: bold;
}
.z-groupbox-3d div.open-false {
  background-position: right -16px;
}
.z-groupbox-colpsd .block {
  display: block;
}
.folder {
  background: url("${ImgUrl}/folder.png") no-repeat 0 -16px;
  height:16px;
  padding-left: 18px;
  _display: inline;
}
.z-groupbox-colpsd .folder{
  background-position: 0 0;
}