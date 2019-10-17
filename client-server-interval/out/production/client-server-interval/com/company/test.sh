for (( i=0; i < 10; i++ ))
do
    java Client 127.0.0.1 8890
    #java Client 127.0.0.1 8890 &
done
wait
echo "Закончено за $SECONDS"
