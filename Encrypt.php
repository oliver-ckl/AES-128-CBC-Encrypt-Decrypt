<?php
    $plaintext="test123!!!";
    $cipher = "AES-128-CBC";
    $key="1234567891234567";
    $ivlen = openssl_cipher_iv_length($cipher);
    $iv = openssl_random_pseudo_bytes($ivlen);
    $ciphertext = openssl_encrypt($plaintext, $cipher, $key, $options=0,$iv);
    $ivToJava=bin2hex($iv); //pass this hex form iv to java
?>
