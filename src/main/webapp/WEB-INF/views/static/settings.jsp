<c:import url="/WEB-INF/views/common/layout.jsp" charEncoding="UTF-8">
    <c:param name="title" value="About"/>
    <c:param name="body">

        <div class="row span9 main-content">
            <h1>Settings</h1>

            <div class="row-fluid">
                Disc Usage:

                <div class="progress">
                    <div class="bar bar-danger"
                         style="width:${(rootfile.totalSpace - rootfile.usableSpace) / rootfile.totalSpace *100}%"></div>
                    <div class="bar bar-success"
                         style="width: ${rootfile.usableSpace / rootfile.totalSpace *100}%;"></div>

                </div>
                <span class="pull-right">
                    Free Space: ${usablespace}
                </span>
            </div>


            <div class="row-fluid">
                Download Blobstore:
                <a href="<c:url value="/admin/blobs.zip"/>">
                    <button class="btn"><i class="icon-download"></i>Download</button>
                </a>
            </div>


            <div class="row-fluid">
                <a href="<c:url value="/admin/shutdown" />">
                    <button class="btn btn-danger pull-right"><i class="icon-off icon-white"></i>&nbsp;Shutdown</button>
                </a>
            </div>


        </div>

    </c:param>
</c:import>

