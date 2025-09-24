# Milestone Progress View 사용 가이드

이 문서는 Milestone Progress View 라이브러리의 자세한 사용 방법을 설명합니다.

## 목차

1. [기본 설정](#기본-설정)
2. [XML 사용법](#xml-사용법)
3. [코틀린/자바 사용법](#코틀린자바-사용법)
4. [커스터마이징](#커스터마이징)
5. [애니메이션](#애니메이션)
6. [실제 사용 예제](#실제-사용-예제)
7. [문제 해결](#문제-해결)

## 기본 설정

### 1. 의존성 추가

`app/build.gradle`에 다음을 추가하세요:

```gradle
dependencies {
    implementation 'com.github.domm-pixel:milestone_progress_view:1.0.0'
}
```

### 2. 저장소 설정

`settings.gradle` (권장) 또는 프로젝트 레벨 `build.gradle`에 JitPack 저장소를 추가하세요:

```gradle
// settings.gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

## XML 사용법

### 기본 XML 설정

```xml
<com.dommpixel.milestoneprogressview.MilestoneProgressView
    android:id="@+id/milestoneProgressView"
    android:layout_width="match_parent"
    android:layout_height="80dp"
    android:padding="8dp" />
```

### 모든 속성을 포함한 완전한 예제

```xml
<com.dommpixel.milestoneprogressview.MilestoneProgressView
    android:id="@+id/milestoneProgressView"
    android:layout_width="match_parent"
    android:layout_height="100dp"
    android:padding="16dp"
    app:progressColor="#2196F3"
    app:backgroundColor="#E0E0E0"
    app:milestoneColor="#4CAF50"
    app:milestoneIncompleteColor="#9E9E9E"
    app:textColor="#333333"
    app:progressBarHeight="10dp"
    app:milestoneRadius="16dp"
    app:textSize="16sp"
    app:progress="0.5" />
```

### XML 속성 상세 설명

| 속성 | 타입 | 기본값 | 설명 |
|------|------|--------|------|
| `app:progressColor` | color | `#2196F3` | 진행된 부분의 색상 |
| `app:backgroundColor` | color | `#E0E0E0` | 진행되지 않은 부분의 배경 색상 |
| `app:milestoneColor` | color | `#4CAF50` | 완료된 마일스톤의 색상 |
| `app:milestoneIncompleteColor` | color | `#9E9E9E` | 미완료 마일스톤의 색상 |
| `app:textColor` | color | `#333333` | 마일스톤 라벨 텍스트 색상 |
| `app:progressBarHeight` | dimension | `8dp` | 진행 바의 높이 |
| `app:milestoneRadius` | dimension | `12dp` | 마일스톤 원의 반지름 |
| `app:textSize` | dimension | `14sp` | 라벨 텍스트 크기 |
| `app:progress` | float | `0.0` | 초기 진행도 (0.0~1.0) |

## 코틀린/자바 사용법

### 코틀린 예제

```kotlin
import com.dommpixel.milestoneprogressview.MilestoneProgressView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressView = findViewById<MilestoneProgressView>(R.id.milestoneProgressView)
        
        // 마일스톤 생성
        val milestones = listOf(
            MilestoneProgressView.Milestone(0.0f, "시작", true),
            MilestoneProgressView.Milestone(0.25f, "25%", false),
            MilestoneProgressView.Milestone(0.5f, "중간", false),
            MilestoneProgressView.Milestone(0.75f, "75%", false),
            MilestoneProgressView.Milestone(1.0f, "완료", false)
        )
        
        // 마일스톤 설정
        progressView.setMilestones(milestones)
        
        // 진행도 설정
        progressView.setProgress(0.3f)
    }
}
```

### 자바 예제

```java
import com.dommpixel.milestoneprogressview.MilestoneProgressView;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MilestoneProgressView progressView = findViewById(R.id.milestoneProgressView);
        
        // 마일스톤 생성
        List<MilestoneProgressView.Milestone> milestones = Arrays.asList(
            new MilestoneProgressView.Milestone(0.0f, "시작", true),
            new MilestoneProgressView.Milestone(0.25f, "25%", false),
            new MilestoneProgressView.Milestone(0.5f, "중간", false),
            new MilestoneProgressView.Milestone(0.75f, "75%", false),
            new MilestoneProgressView.Milestone(1.0f, "완료", false)
        );
        
        // 마일스톤 설정
        progressView.setMilestones(milestones);
        
        // 진행도 설정
        progressView.setProgress(0.3f);
    }
}
```

## 커스터마이징

### 프로그래밍 방식으로 색상 변경

```kotlin
progressView.apply {
    progressColor = Color.parseColor("#FF5722")
    backgroundColor = Color.parseColor("#FFCCBC")
    milestoneColor = Color.parseColor("#4CAF50")
    milestoneIncompleteColor = Color.parseColor("#9E9E9E")
    textColor = Color.parseColor("#333333")
}
```

### 크기 및 스타일 변경

```kotlin
progressView.apply {
    progressBarHeight = 12f
    milestoneRadius = 18f
    textSize = 16f * resources.displayMetrics.scaledDensity
}
```

### 동적 테마 변경

```kotlin
fun applyDarkTheme(progressView: MilestoneProgressView) {
    progressView.apply {
        progressColor = Color.parseColor("#BB86FC")
        backgroundColor = Color.parseColor("#3700B3")
        milestoneColor = Color.parseColor("#03DAC6")
        milestoneIncompleteColor = Color.parseColor("#666666")
        textColor = Color.parseColor("#FFFFFF")
    }
}
```

## 애니메이션

### 기본 애니메이션

```kotlin
// 2초 동안 50%까지 애니메이션
progressView.animateProgress(0.5f, 2000L)
```

### 순차적 애니메이션

```kotlin
// 여러 단계로 나누어 애니메이션
fun animateSequentially(progressView: MilestoneProgressView) {
    progressView.animateProgress(0.25f, 1000L)
    
    Handler(Looper.getMainLooper()).postDelayed({
        progressView.animateProgress(0.5f, 1000L)
    }, 1100L)
    
    Handler(Looper.getMainLooper()).postDelayed({
        progressView.animateProgress(1.0f, 1000L)
    }, 2200L)
}
```

### 사용자 인터랙션과 결합

```kotlin
seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            val targetProgress = progress / 100f
            progressView.animateProgress(targetProgress, 300L)
        }
    }
    
    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
})
```

## 실제 사용 예제

### 1. 학습 진행도 추적

```kotlin
class LearningProgressTracker {
    
    fun setupLearningProgress(progressView: MilestoneProgressView) {
        val chapters = listOf(
            MilestoneProgressView.Milestone(0.0f, "입문", true),
            MilestoneProgressView.Milestone(0.2f, "기초", true),
            MilestoneProgressView.Milestone(0.4f, "중급", false),
            MilestoneProgressView.Milestone(0.6f, "고급", false),
            MilestoneProgressView.Milestone(0.8f, "전문가", false),
            MilestoneProgressView.Milestone(1.0f, "마스터", false)
        )
        
        progressView.setMilestones(chapters)
        progressView.setProgress(0.3f)
    }
    
    fun updateProgress(progressView: MilestoneProgressView, completedChapters: Int, totalChapters: Int) {
        val progress = completedChapters.toFloat() / totalChapters.toFloat()
        progressView.animateProgress(progress, 1000L)
    }
}
```

### 2. 프로젝트 관리

```kotlin
class ProjectManager {
    
    fun setupProjectMilestones(progressView: MilestoneProgressView) {
        val phases = listOf(
            MilestoneProgressView.Milestone(0.0f, "시작", true),
            MilestoneProgressView.Milestone(0.15f, "요구사항", true),
            MilestoneProgressView.Milestone(0.3f, "설계", false),
            MilestoneProgressView.Milestone(0.6f, "개발", false),
            MilestoneProgressView.Milestone(0.8f, "테스트", false),
            MilestoneProgressView.Milestone(0.95f, "배포준비", false),
            MilestoneProgressView.Milestone(1.0f, "완료", false)
        )
        
        progressView.apply {
            setMilestones(phases)
            progressColor = Color.parseColor("#2196F3")
            milestoneColor = Color.parseColor("#4CAF50")
        }
    }
}
```

### 3. 게임 레벨 시스템

```kotlin
class GameLevelSystem {
    
    fun setupLevelProgress(progressView: MilestoneProgressView, currentLevel: Int, maxLevel: Int) {
        val levels = mutableListOf<MilestoneProgressView.Milestone>()
        
        for (i in 1..maxLevel step (maxLevel/5)) {
            val position = (i - 1).toFloat() / (maxLevel - 1).toFloat()
            val isCompleted = i <= currentLevel
            levels.add(MilestoneProgressView.Milestone(position, "Lv.$i", isCompleted))
        }
        
        progressView.apply {
            setMilestones(levels)
            progressColor = Color.parseColor("#FF9800")
            milestoneColor = Color.parseColor("#FFB74D")
            setProgress((currentLevel - 1).toFloat() / (maxLevel - 1).toFloat())
        }
    }
}
```

## 문제 해결

### 자주 발생하는 문제들

#### 1. 마일스톤이 표시되지 않는 경우

**원인**: 마일스톤이 설정되지 않았거나 View의 높이가 부족함

**해결방법**:
```kotlin
// 마일스톤이 설정되었는지 확인
if (progressView.milestones.isEmpty()) {
    progressView.setMilestones(yourMilestones)
}

// View 높이를 충분하게 설정 (최소 80dp 권장)
```

#### 2. 텍스트가 잘리는 경우

**원인**: View의 높이가 부족하거나 텍스트 크기가 너무 큼

**해결방법**:
```xml
<!-- 충분한 높이 제공 -->
<com.dommpixel.milestoneprogressview.MilestoneProgressView
    android:layout_height="100dp"
    app:textSize="12sp" />
```

#### 3. 애니메이션이 부드럽지 않은 경우

**원인**: 메인 스레드에서 다른 작업이 실행중이거나 duration이 너무 짧음

**해결방법**:
```kotlin
// 적절한 duration 설정
progressView.animateProgress(targetProgress, 1000L) // 1초

// UI 스레드에서 실행되도록 확인
runOnUiThread {
    progressView.animateProgress(targetProgress, 1000L)
}
```

#### 4. 메모리 누수

**원인**: Activity나 Fragment에서 애니메이션이 완료되기 전에 소멸됨

**해결방법**:
```kotlin
override fun onDestroy() {
    super.onDestroy()
    // 진행중인 애니메이션 취소
    progressView.clearAnimation()
}
```

### 성능 최적화 팁

1. **적절한 마일스톤 개수**: 너무 많은 마일스톤(10개 이상)은 피하세요
2. **애니메이션 duration**: 1-3초 사이의 적절한 시간을 사용하세요
3. **색상 캐싱**: 자주 변경되는 색상은 미리 계산해두세요
4. **View 재사용**: RecyclerView에서 사용할 때는 ViewHolder 패턴을 활용하세요

### 디버깅 도구

```kotlin
// 현재 상태 로그 출력
fun debugProgressView(progressView: MilestoneProgressView) {
    Log.d("MilestoneProgressView", "Progress: ${progressView.progress}")
    Log.d("MilestoneProgressView", "Milestones count: ${progressView.milestones.size}")
    progressView.milestones.forEachIndexed { index, milestone ->
        Log.d("MilestoneProgressView", "Milestone $index: ${milestone.label} at ${milestone.position}")
    }
}
```

이 가이드를 참고하여 Milestone Progress View를 효과적으로 활용하세요!