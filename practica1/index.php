<?php
header('Content-Type: text/html; charset=UTF-8');
ini_set('display_errors', 1);
require_once('TwitterAPIExchange.php');
ini_set('max_execution_time', 500);
$num_tweets = 1;

//PUT YOUR TWITTER USER_NAME HERE
$user_name = 'jesus_pkmd';



/** Perform a GET request and echo the response **/
/** Note: Set the GET field BEFORE calling buildOauth(); **/
function getTweets($name,$maxid=''){
    
    /** Set access tokens here - see: https://dev.twitter.com/apps/ **/
    
    $settings = array(
    'oauth_access_token' => "321651149-3WDJN7geliDPmQSlO7TWDuOK8cAFgtKYQQFh2Tz1",
    'oauth_access_token_secret' => "88lttFFu3vs04sJXVTX4tumaNtLjNYFvkfGwwRs6jTODL",
    'consumer_key' => "ViOlblew2vsIZ94mgyd9tA",
    'consumer_secret' => "702OsJQiF03f4VOVefUG2EwnQkRnHefnHglW6kwi58U"
    );
    
    $url = 'https://api.twitter.com/1.1/statuses/user_timeline.json';
    $getfield = '?screen_name='.$name.$maxid;
    $requestMethod = 'GET';
    $twitter = new TwitterAPIExchange($settings);
    $query = $twitter->setGetfield($getfield)
                 ->buildOauth($url, $requestMethod)
                 ->performRequest();

    return json_decode($query,true);
    
}

do{
    if(isset($tweet['id_str'])){
        $tweets = getTweets($user_name,'&max_id='.$tweet['id_str']);
    }
    else{
        $tweets = getTweets($user_name);
    }

    foreach($tweets as $tweet) {
        echo $num_tweets.' '.$tweet['text'];
        echo "<br>";
        $num_tweets++;
    }
}while(isset($tweet['id_str']));

//$tweets = getTweets('carlos_cb20','&max_id='.$tweet['id_str']);
//
//foreach($tweets as $tweet) {
//    echo $tweet['text'];
//    echo "<br>";
//}

        


?>