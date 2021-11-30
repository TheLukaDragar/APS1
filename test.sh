PROGNUM=$1
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

for i in 1 2 3 4 5 6 7 8 9 10
do
	VHOD=Naloga${PROGNUM}/testi/I1_$i.txt         # pot do vhoda
	IZHOD=Naloga${PROGNUM}/testi/O1_$i.txt        # pot do uradnega izhoda
	REZULTAT=Naloga${PROGNUM}/testi/R1_$i.txt     # rezultat programa za podani vhod

	time java -cp Naloga${PROGNUM} Naloga${PROGNUM} ${VHOD} ${REZULTAT} > /dev/null
	echo -n "Primer $i: "   
	
	diff --strip-trailing-cr $IZHOD $REZULTAT > /dev/null 2>&1
	ERR=$?
	if [ $ERR -eq 0 ]; then
		echo -e "${GREEN}OK${NC}"
	else
		echo -e "${RED}KO${NC}"
	fi

done
