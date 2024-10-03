function openMenu() {
	$(".side-bar").css("left", "0");
}

function closeMenu() {
	$(".side-bar").css("left", "-70%");
}

// validate
function Validator(options) {
	var selectorRules = {};

	function validate(inputElement, rule) {
		var errorMessage;
		var rules = selectorRules[rule.selector];
		for (const rule of rules) {
			errorMessage = rule(inputElement.val());
			if (!errorMessage) break;
		}

		if (!errorMessage) {
			inputElement.parent().addClass("invalid");
		} else {
			inputElement.parent().removeClass("invalid");
		}

		return !errorMessage;
	}

	function validateInput(inputElement) {
		inputElement.parent().removeClass("invalid");
	}

	var formElement = $(options.form);
	if (formElement.length) {
		formElement.on("submit", function (e) {
			e.preventDefault();
			var isFormValid = true;
			options.rules.forEach(function (rule) {
				var inputElement = formElement.find(rule.selector);
				var isValid = validate(inputElement, rule);
				if (isValid) {
					isFormValid = false;
				}
			});

			if (isFormValid) {
				if (typeof options.onSubmit === "function") {
					var enableInputs = formElement.find("[name]:not([disabled])");

					var formValues = {};
					enableInputs.each(function () {
						var input = $(this);
						formValues[input.attr("name")] = input.val();
					});
					options.onSubmit(formValues);
					formElement.get(0).submit(); // submit mặc định
				}
			}
		});

		options.rules.forEach(function (rule) {
			// Save rules
			if (Array.isArray(selectorRules[rule.selector])) {
				selectorRules[rule.selector].push(rule.test);
			} else {
				selectorRules[rule.selector] = [rule.test];
			}

			var inputElement = formElement.find(rule.selector);

			if (inputElement.length) {
				inputElement.on("blur", function () {
					validate($(this), rule);
				});
				inputElement.on("input", function () {
					validateInput($(this));
				});
			}
		});

		formElement.on("reset", function () {
			var inputBlocks = $("." + options.resetElement);
			inputBlocks.removeClass("invalid");
		});
	}
}

Validator.isRequired = function (selector) {
	return {
		selector: selector,
		test: function (value) {
			return value.trim() !== "";
		},
	};
};

Validator.isEmail = function (selector) {
	return {
		selector: selector,
		test: function (value) {
			var regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
			return regex.test(value);
		},
	};
};

Validator.lengthRange = function (selector, min, max) {
	return {
		selector: selector,
		test: function (value) {
			if (max === undefined) {
				return value.length >= min;
			}
			return value.length >= min && value.length <= max;
		},
	};
};

Validator.isConfirm = function (selector, getValueCallback) {
	return {
		selector: selector,
		test: function (value) {
			return value === getValueCallback();
		},
	};
};

Validator.isViePhoneNum = function (selector) {
	return {
		selector,
		test: function (value) {
			return /(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\b/.test(value);
		},
	};
};
