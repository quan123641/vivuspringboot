var configViewContent = {
	url: "view-content.html",
    identityElement: '#view-content' ,
	tableBodySelector: "#content-table-body",
	loadingSelector: ".notify-loading",
	mainComponentSelector: ".main-component-block",
	defaultDate: "2022-11-11",
	// delay: 5,
	handleData: function (data, config) {
		var tbody = $(config.tableBodySelector);
		tbody.empty();

		// data.forEach(function (item, index) {
		// 	var row = $("<tr>");
		// 	row.append($("<td>").text(index + 1));
		// 	row.append($("<td>").text(item.title));
		// 	row.append($("<td>").text(item.brief));
		// 	row.append($("<td>").text(config.defaultDate));
		// 	tbody.append(row);
		// });
	},
};

var configAddContent = {
	url: "#!",
    identityElement: '#add-content' ,
	tableBodySelector: "#content-table-body",
	loadingSelector: ".notify-loading",
	mainComponentSelector: ".main-component-block",
	defaultDate: "2022-11-11",
	// delay: 5,
    handleData: function(data){
        console.log(data)
    }
};

$(document).ready(function () {
	fetchDataAndHandleData(configViewContent);
	fetchDataAndHandleData(configAddContent);
});

function loadData(url) {
	return $.ajax({
		url: url,
		type: "GET",
	});
}

function fetchDataAndHandleData(config) {
	if ($(config.identityElement).length === 0) {
		// console.log("Selector not found: " + config.identityElement);
		return;
	}
    
	setTimeout(function () {
		loadData(config.url)
			.done(function (data) {
				// console.log(data);
				config.handleData(data, config);
				alert("Thành công: ", data);
				$(config.loadingSelector).hide();
				$(config.mainComponentSelector).show();
			})
			.fail(function (error) {
				alert("Lỗi khi tải dữ liệu:", error);
				$(config.loadingSelector).hide();
				$(config.mainComponentSelector).show();
			});
	}, config.delay);
}

