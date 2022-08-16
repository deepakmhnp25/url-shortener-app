package com.org.shortener.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.concurrent.ExecutionException;

/**
 * Repository class which contains all the db related common methods
 * like fetching and saving to the db.
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
@org.springframework.stereotype.Repository
public class CommonRepository {

    /**
     * Common method to fetch details from database.
     *
     * @param collectionName name of the collection where data is stored
     * @param documentIdentifier identifier for document in datastore
     * @return DocumentSnapshot the document from db
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public DocumentSnapshot getDocument(String collectionName, String documentIdentifier)
            throws InterruptedException, ExecutionException {
        DocumentReference documentRef = FirestoreClient.getFirestore().collection(collectionName)
                .document(documentIdentifier);
        ApiFuture<DocumentSnapshot> future = documentRef.get();
        return future.get();
    }

    /**
     * Common method to store a document in db
     *
     * @param object any object
     * @param collectionName name of the collection where data to be stored
     * @param documentIdentifier identifier for document in datastore
     * @param <T>
     */
    public <T> void createDocument(T object, String collectionName, String documentIdentifier){
        FirestoreClient.getFirestore().collection(collectionName)
                .document(documentIdentifier).set(object);
    }
}
