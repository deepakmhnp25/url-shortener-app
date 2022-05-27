package com.shortcut.shortener.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.concurrent.ExecutionException;

/**
 * Repository class which contains all the db related common methods
 */
@org.springframework.stereotype.Repository
public class CommonRepository {

    /**
     * Common method to fetch details from database.
     * @param collectionName
     * @param documentIdentifier
     * @return DocumentSnapshot
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
     * @param object
     * @param collectionName
     * @param documentIdentifier
     * @param <T>
     */
    public <T> void createDocument(T object, String collectionName, String documentIdentifier){
        FirestoreClient.getFirestore().collection(collectionName)
                .document(documentIdentifier).set(object);
    }
}
