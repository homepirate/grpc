package org.example;
import io.grpc.stub.StreamObserver;
import java.util.HashSet;
import java.util.Set;

public class SpamCheckServiceImpl extends SpamCheckServiceGrpc.SpamCheckServiceImplBase {

    private Set<String> forbiddenWords = new HashSet<>();

    public SpamCheckServiceImpl() {
        forbiddenWords.add("spam");
        forbiddenWords.add("follow me");
        forbiddenWords.add("website");
        forbiddenWords.add("free");
    }

    @Override
    public void checkFeedback(FeedbackCheckRequest request, StreamObserver<FeedbackCheckResponse> responseObserver) {
        String message = request.getMessage();
        FeedbackCheckResponse.Builder responseBuilder = FeedbackCheckResponse.newBuilder();

        Set<String> foundForbiddenWords = new HashSet<>();
        for (String word : forbiddenWords) {
            if (message.toLowerCase().contains(word)) {
                foundForbiddenWords.add(word);
            }
        }

        responseBuilder.setIsSpam(!foundForbiddenWords.isEmpty());
        responseBuilder.addAllForbiddenWords(foundForbiddenWords);

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}

