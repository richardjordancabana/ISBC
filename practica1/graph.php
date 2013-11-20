<?php
// devuelve un código html/javascript con el dibujo de las estadísticas
 function drawChart($pos, $neg, $neutro){
	$codigo = '<html>'."\n".'<head>'."\n";
	$codigo .= '<script type="text/javascript" src="https://www.google.com/jsapi"></script>'."\n";
	$codigo .= '<script type="text/javascript">'."\n";
	$codigo .= 'google.load(\'visualization\', \'1.0\', {\'packages\':[\'corechart\']});'."\n";
	$codigo .= 'google.setOnLoadCallback(drawChart);'."\n";
	$codigo .= 'function drawChart() {'."\n";
	$codigo .= 'var data = new google.visualization.DataTable();'."\n";
	$codigo .= 'data.addColumn(\'string\', \'Topping\');'."\n";
	$codigo .= 'data.addColumn(\'number\', \'Slices\');'."\n";
	$codigo .= 'data.addRows([[\'Positivos\', '.$pos.'],[\'Negativos\', '.$neg.'],[\'Neutrales\', '.$neutro.']]);'."\n";
	$codigo .= 'var options = {\'title\':\'Resultados del analisis\', \'width\':800, \'height\':500};'."\n";
	$codigo .= 'var chart = new google.visualization.PieChart(document.getElementById(\'chart_div\'));'."\n";
	$codigo .= 'chart.draw(data, options);'."\n";
	$codigo .= '}'."\n";
	$codigo .= '</script>'."\n".'</head>'."\n".'<body>'."\n".'<div id="chart_div"></div>'."\n".'</body>'."\n".'</html>'."\n";
	return $codigo;
}
?>
