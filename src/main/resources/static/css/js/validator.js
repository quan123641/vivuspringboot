function submitAjaxCall(data){
	$.ajax({
		url: "http://localhost:3000/content",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function (response) {
			alert("Dữ liệu đã được gửi thành công ", response);
		},
		error: function (error) {
			// console.log("Lỗi khi gửi dữ liệu:", error);
			alert("Lỗi khi gửi dữ liệu ", error);
		},
	});
}

Validator({
	form: "#edit-profile__form",
	errorSelector: ".error",
	rules: [
		Validator.lengthRange("#firstname", 3, 30),
		Validator.lengthRange("#lastname", 3, 30),
		Validator.isViePhoneNum("#phone"),
		Validator.lengthRange("#phone", 9, 13),
		Validator.lengthRange("#desc", 0, 200),
	],
	onSubmit: function (data) {
		submitAjaxCall(data)
	},
	resetElement: "input-block",
});

Validator({
	form: "#add-content__form",
	errorSelector: ".error",
	rules: [
		Validator.lengthRange("#title", 5, 200),
		Validator.lengthRange("#brief", 5, 150),,
		Validator.lengthRange("#content", 5, 1000),
	],
	onSubmit: function (data) {
		submitAjaxCall(data)
	},
	resetElement: "input-block",
});

Validator({
	form: "#register-form",
	errorSelector: ".error",
	rules: [
		Validator.lengthRange("#user-name", 3),
		Validator.isEmail("#email"),
		Validator.lengthRange("#password", 8, 30),
		Validator.lengthRange("#re-password", 8, 30),
		Validator.isConfirm("#re-password", function () {
			return document.querySelector("#password").value;
		}),
	],
	onSubmit: function (data) {
		submitAjaxCall(data)
	},
});

Validator({
    form: "#sign-in__form",
    errorSelector: ".error",
    rules: [
        Validator.isEmail("#email"),
        Validator.lengthRange("#email", 5),
        Validator.lengthRange("#password", 8, 30),
    ],
    onSubmit: function (data) {
		submitAjaxCall(data)
    },
});