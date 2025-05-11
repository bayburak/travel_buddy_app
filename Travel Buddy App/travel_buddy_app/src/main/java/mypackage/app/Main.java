package mypackage.app;

import java.io.IOException;

import java.util.concurrent.ExecutionException;

import mypackage.service.DatabaseService;
import mypackage.service.StorageService;

public class Main {

  

    public static void main( String[] args ) throws IOException, InterruptedException, ExecutionException
    {

        DatabaseService.initialize();
        StorageService.initialize();

       
   
    }




}
