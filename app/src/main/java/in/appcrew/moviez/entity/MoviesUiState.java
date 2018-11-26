package in.appcrew.moviez.entity;

public class MoviesUiState {
    private int currentPage = 0;
    private boolean showProgress = false;
    private boolean isLoading = false;
    private boolean loadingError = false;

    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean getLoading(){
        return isLoading;
    }

    public boolean isLoadingError() {
        return loadingError;
    }

    public void setLoadingError(boolean loadingError) {
        this.loadingError = loadingError;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isShowProgress() {
        return showProgress;
    }
}
