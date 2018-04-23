function switch_workbook() {
	$('#div_workbook').show();
	$('#div_confnote').hide();
	$('#div_projinfo').hide();

	$('#nav_workbook').addClass('active');
	$('#nav_confnote').removeClass('active');
	$('#nav_projinfo').removeClass('active');
}

function switch_confnote() {
	$('#div_workbook').hide();
	$('#div_confnote').show();
	$('#div_projinfo').hide();

	$('#nav_workbook').removeClass('active');
	$('#nav_confnote').addClass('active');
	$('#nav_projinfo').removeClass('active');
}

function switch_projinfo() {
	$('#div_workbook').hide();
	$('#div_confnote').hide();
	$('#div_projinfo').show();

	$('#nav_workbook').removeClass('active');
	$('#nav_confnote').removeClass('active');
	$('#nav_projinfo').addClass('active');
}
