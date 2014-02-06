<%@ attribute name="c" required="true" type="org.jojen.wikistudy.entity.Quiz"%>
<%@ attribute name="link" required="false" type="java.lang.String"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ tag trimDirectiveWhitespaces="true"%>

<!-- TODO sollte in class übergehen - momentan nur ein quiz pro site möglich -->
<div id="slick-quiz">
    <h3 class="quizName"></h3>

    <div class="quizArea">
        <div class="quizHeader">
            <!-- where the quiz main copy goes -->

            <button class="btn btn-primary startQuiz" href="#"><i
                    class="icon-play icon-white pull-right"></i>Get Started&nbsp;
            </button>
        </div>

        <!-- where the quiz gets built -->
    </div>

    <div class="quizResults">
        <h4 class="quizScore">You Scored:
            <span><!-- where the quiz score goes --></span></h4>

        <h4 class="quizLevel"><strong>Ranking:</strong> <span><!-- where the quiz ranking level goes --></span>
        </h4>

        <div class="quizResultsCopy">
            <!-- where the quiz result copy goes -->
        </div>
    </div>
</div>
<script type="text/javascript">
    $('#slick-quiz').slickQuiz({json:
        ${c.quizContent}
    });
</script>
