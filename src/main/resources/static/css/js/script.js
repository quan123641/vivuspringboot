function openMenu() {
	document.querySelector(".side-bar").style.left = "0";
}

function closeMenu() {
	document.querySelector(".side-bar").style.left = "-70%";
}

// validate
function Validator(options) {
	var selectorRules = {};

	function validate(inputElement, rule) {
		var errorMessage;
		var rules = selectorRules[rule.selector];
		for (const rule of rules) {
			errorMessage = rule(inputElement.value);
			if (!errorMessage) break;
		}

		if (!errorMessage) {
			inputElement.parentElement.classList.add("invalid");
		} else {
			inputElement.parentElement.classList.remove("invalid");
		}

		return !errorMessage;
	}

	function validateInput(inputElement) {
		inputElement.parentElement.classList.remove("invalid");
	}

	var formElement = document.querySelector(options.form);
	if (formElement) {
		formElement.onsubmit = function (e) {
			e.preventDefault();
			var isFormValid = true;
			options.rules.forEach(function (rule) {
				var inputElement = formElement.querySelector(rule.selector);
				var isValid = validate(inputElement, rule);
				if (isValid) {
					isFormValid = false;
				}
			});

			if (isFormValid) {
				if (typeof options.onSubmit === "function") {
					var enableInputs = formElement.querySelectorAll(
						"[name]:not([disable])"
					);

					var formValues = Array.from(enableInputs).reduce(function (
						values,
						input
					) {
						return (values[input.name] = input.value) && values;
					},
					{});
					options.onSubmit(formValues);
					formElement.submit();
				}
			}
		};

		options.rules.forEach(function (rule) {
			// Save rules
			if (Array.isArray(selectorRules[rule.selector])) {
				selectorRules[rule.selector].push(rule.test);
			} else {
				selectorRules[rule.selector] = [rule.test];
			}

			var inputElement = formElement.querySelector(rule.selector);

			if (inputElement) {
				inputElement.onblur = () => {
					console.log(inputElement);
					validate(inputElement, rule);
				};
				inputElement.oninput = () => {
					validateInput(inputElement);
				};
			}
		});

		formElement.addEventListener("reset", () => {
			var inputBlocks = document.querySelectorAll(
				`.${options.resetElement}`
			);
			for (const inputBlock of inputBlocks) {
				inputBlock.classList.remove("invalid");
			}
		});
	}
}

Validator.isRequired = function (selector) {
	return {
		selector: selector,
		test: function (value) {
			return value.trim();
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
