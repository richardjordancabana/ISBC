<?php
header('Content-Type: text/html; charset=UTF-8');
ini_set('display_errors', 1);
//twitter
require_once('TwitterAPIExchange.php');
//lexicón
require_once 'procesamiento.php';	
//gráfico
require_once 'graph.php';

$settings = array(
    'oauth_access_token' => "321651149-3WDJN7geliDPmQSlO7TWDuOK8cAFgtKYQQFh2Tz1",
    'oauth_access_token_secret' => "88lttFFu3vs04sJXVTX4tumaNtLjNYFvkfGwwRs6jTODL",
    'consumer_key' => "ViOlblew2vsIZ94mgyd9tA",
    'consumer_secret' => "702OsJQiF03f4VOVefUG2EwnQkRnHefnHglW6kwi58U"
    );

ini_set('max_execution_time', 500);

$analize = $_POST['analize'];//tr: jesus_pkmd,RicTheImpaler,#LeyAnti15M
$type = $_POST['type'];
$limit = $_POST['limit'];

$lexicon = cargar("lexicon.txt");
$num_tweets = 0;
$num_pos = 0;
$num_neg = 0;
$num_neutro = 0;
$valor_total = 0;
$lista_tweets = '';
$cont_pags = 0;

do{
    if(isset($tweet['id_str'])){
        $tweets = getTweets($settings,$analize,$type,'&max_id='.$tweet['id_str']);
    }
    else{
        $tweets = getTweets($settings,$analize,$type);
    }
    foreach($tweets as $tweet) {
		$valor = calculaValor($lexicon, $tweet['text']);
		if ($valor > 0) { //Tweet Positivo
			$num_pos ++;
		}
		else if ($valor < 0) { //Tweet Negativo
			$num_neg ++;
		}
		else { //Tweet Neutro
			$num_neutro ++;
		}
		$valor_total = $valor_total + $valor; //Cálculo del valor total
		$lista_tweets .= $num_tweets+1 .' ['.$valor.'] '.$tweet['text']."<br>";
        $num_tweets++;		
    }
	$cont_pags++;
}while(isset($tweet['id_str']) && $num_tweets<=$limit);

//ECHOS/////////////////
echo "<br>".'Cargados '.$num_tweets.' tweets de '.$cont_pags.' paginas.'."<br>"."<br>";
echo 'Numero de Tweets positivos: '.$num_pos."<br>";
echo 'Numero de Tweets negativos: '.$num_neg."<br>";
echo 'Numero de Tweets neutros: '.$num_neutro."<br>";
echo 'Valor total: '.$valor_total."<br>";
echo drawChart($num_pos, $num_neg, $num_neutro);
echo 'Lista de tweets cargados: '."<br>";
echo $lista_tweets;
////////////////////////


/** Perform a GET request and echo the response **/
/** Note: Set the GET field BEFORE calling buildOauth(); **/
function getTweets($settings,$name,$type,$maxid=''){
    
    if($type==="username"){
        $url = 'https://api.twitter.com/1.1/statuses/user_timeline.json';
        $name = substr($name, 1);
        $getfield = '?screen_name='.$name.$maxid;
    }
    else if($type==="mention"){
        $url = 'https://api.twitter.com/1.1/statuses/mentions_timeline.json';
        $name = substr($name, 1);
        $getfield = '?screen_name='.$name.$maxid;
    }
    else{//hashtag
        $url = 'https://api.twitter.com/1.1/search/tweets.json';
        $getfield = '?q='.$name.$maxid;
    }

    $requestMethod = 'GET';
    $twitter = new TwitterAPIExchange($settings);
    $query = $twitter->setGetfield($getfield)
                 ->buildOauth($url, $requestMethod)
                 ->performRequest();

    return json_decode($query,true);
    
}

// devuelve el valor del tweet
function calculaValor($lexicon, $tweet){
	$palabras = explode(" ", $tweet);
	$valor = 0;
	$contador = count($palabras);
	for($i=0; $i < $contador; $i++){
		$valor += consultar($lexicon, $palabras[$i]);
	}
	return $valor;
}
?>