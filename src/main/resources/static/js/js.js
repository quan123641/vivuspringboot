// Open the modal
function openCreateCategoryModal(button) {
 var id = button.getAttribute('data-id');
    var title = button.getAttribute('data-title');
    var brief = button.getAttribute('data-brief');
    var description = button.getAttribute('data-description');
     document.getElementById('editId').value = id;
    // Tiếp tục xử lý với các giá trị này
    console.log(id, title, brief, description);
    document.getElementById('createCategoryModal').style.display = 'block';
}

// Validate the form
function validateCreateCategoryForm() {
   
    
    // Validate the category name input and description input
    const categoryNameInput = document.getElementById('title');
    const categoryName = categoryNameInput.value.trim();
    if (categoryName.length === 0) {
        // get the error message element and set the error message
        const errorMessageElement = document.getElementById('categoryNameError');
        errorMessageElement.innerText = 'Name is required';
        errorMessageElement.style.display = 'block';
        categoryNameInput.focus();
        return false;
    } else {
        // get the error message element and hide it
        const errorMessageElement = document.getElementById('categoryNameError');
        errorMessageElement.style.display = 'none';
    }

    if (categoryName.length < 3 || categoryName.length > 255) {
        // get the error message element and set the error message
        const errorMessageElement = document.getElementById('categoryNameError');
        errorMessageElement.innerText = 'Name must be between 3 and 255 characters';
        errorMessageElement.style.display = 'block';
        categoryNameInput.focus();
        return false;
    } else {
        // get the error message element and hide it
        const errorMessageElement = document.getElementById('categoryNameError');
        errorMessageElement.style.display = 'none';
    }

    const briefContentInput = document.getElementById('brief').value;
    const briefContent = briefContentInput.trim();
    if (briefContent.length === 0) {
        // get the error message element and set the error message
        const errorMessageElement = document.getElementById('categoryNameError1');
        errorMessageElement.innerText = 'Brief is required';
        errorMessageElement.style.display = 'block';
        briefContentInput.focus();
        return false;
    } else {
        // get the error message element and hide it
        const errorMessageElement = document.getElementById('categoryNameError1');
        errorMessageElement.style.display = 'none';
    }

    const categoryDescriptionInput = document.getElementById('description');
    const categoryDescription = categoryDescriptionInput.value.trim();

    if (categoryDescription.length > 500) {
        // get the error message element and set the error message
        const errorMessageElement = document.getElementById('categoryDescriptionError');
        errorMessageElement.innerText = 'Description must be less than 500 characters';
        errorMessageElement.style.display = 'block';
        categoryDescriptionInput.focus();
        return false;
    } else {
        // get the error message element and hide it
        const errorMessageElement = document.getElementById('categoryDescriptionError');
        errorMessageElement.style.display = 'none';
    }

    return true;
}

function createCategory() {
    let valid = validateCreateCategoryForm();

    if (!valid) {
        return;
    }


    let title = document.getElementById('title').value;
    let brief = document.getElementById('brief').value;
    let description = document.getElementById('description').value;
//    let id = document.getElementById('editId').value;
    let id = document.getElementById('editId').value;

    const dataStr = JSON.stringify({
        id: id,
        title: title,
        description: description,
        brief: brief
    });
   var urlStr = "http://localhost:8080/api/edit/"+id;
   console.log("URL",urlStr);
 $.ajax({
     url: urlStr,
     type: 'POST',
     data: dataStr, // Đảm bảo dataStr là chuỗi JSON
     contentType: "application/json", // Đặt Content-Type
     success: function (result) {
        console.log("AJAX call successful!", result);
        closeModal();
        window.location.reload();

     },
     error: function (error) {
         console.error("ERROR", error);
     }
 });

}

// Close the modal
function closeModal() {
    console.log("Closing modal...");
    document.getElementById('createCategoryModal').style.display = 'none';
}