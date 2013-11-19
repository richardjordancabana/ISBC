<?php
header('Content-Type: text/html; charset=UTF-8');
ini_set('display_errors', 1);
require_once('TwitterAPIExchange.php');

$settings = array(
    'oauth_access_token' => "321651149-3WDJN7geliDPmQSlO7TWDuOK8cAFgtKYQQFh2Tz1",
    'oauth_access_token_secret' => "88lttFFu3vs04sJXVTX4tumaNtLjNYFvkfGwwRs6jTODL",
    'consumer_key' => "ViOlblew2vsIZ94mgyd9tA",
    'consumer_secret' => "702OsJQiF03f4VOVefUG2EwnQkRnHefnHglW6kwi58U"
    );

ini_set('max_execution_time', 500);
$num_tweets = 1;


$analize = $_POST['analize'];//tr: jesus_pkmd,RicTheImpaler,#LeyAnti15M
$type = $_POST['type'];
$limit = $_POST['limit'];


//$analize = "@jesus_pkmd";//tr: jesus_pkmd,RicTheImpaler,#LeyAnti15M
//$type = "username";
//$limit = "200";

//$user_name = 'jesus_pkmd';



do{
    if(isset($tweet['id_str'])){
        $tweets = getTweets($settings,$analize,$type,'&max_id='.$tweet['id_str']);
    }
    else{
        $tweets = getTweets($settings,$analize,$type);
    }

    foreach($tweets as $tweet) {
        echo $num_tweets.' '.$tweet['text'];
        echo "<br>";
        $num_tweets++;
    }
}while(isset($tweet['id_str']) && $num_tweets<=$limit);



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

?>