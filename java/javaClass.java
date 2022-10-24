/* <autorite> typeDeRetour NomDeLaFonction(typeParam1 Param1, typeParam2 Param2...){
<autorite> est optionnel

return object
object est de type "typeDeRetour" */

//EXEMPLE


public ClientHist findIdFiscal1(@RequestParam String numero, @RequestParam String dateRejetStr){

        Client cli =  clientRepository.findClientByBadge(numero);
        BigDecimal numClient = cli.getCliCodCli();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd/MM/yyyy, HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        Date dateRejet = new Date();
        try {
            dateRejet = simpleDateFormat.parse(dateRejetStr);
            System.out.println(dateRejet);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ClientHist();
        }
        return clientHistRepository.findClientHist(numClient,dateRejet).get(0);

        //return cli;
    }

dans ce cas : ClientHist est le type de clientHistRepository.findClientHist(numClient,dateRejet).get(0);
