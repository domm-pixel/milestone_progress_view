# Milestone Progress View

[![](https://jitpack.io/v/domm-pixel/milestone_progress_view.svg)](https://jitpack.io/#domm-pixel/milestone_progress_view)

마일스톤 기반 진행도를 시각화하는 안드로이드 커스텀 뷰 라이브러리입니다.

An Android library that visualizes progress with milestones and labels, perfect for showing project progress, learning paths, or any multi-step process.

## 특징 (Features)

- 🎯 **마일스톤 지원**: 진행 과정의 주요 단계를 표시
- 📊 **진행도 시각화**: 현재 진행률을 직관적으로 표현
- 🎨 **커스터마이징 가능**: 색상, 크기, 텍스트 스타일 변경 가능
- ⚡ **애니메이션 지원**: 부드러운 진행도 애니메이션
- 📱 **가벼운 라이브러리**: 최소한의 의존성
- 🔧 **XML/코틀린 설정**: XML 속성과 프로그래밍 방식 모두 지원

## 스크린샷 (Screenshots)

![Milestone Progress View Example](https://user-images.githubusercontent.com/placeholder/milestone-progress-view-demo.gif)

## 설치 (Installation)

### Gradle 설정

#### 1. 프로젝트 레벨 `build.gradle` 또는 `settings.gradle`에 JitPack 저장소 추가:

```gradle
// settings.gradle (권장)
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

또는 프로젝트 레벨 `build.gradle`:

```gradle
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

#### 2. 앱 레벨 `build.gradle`에 의존성 추가:

```gradle
dependencies {
    implementation 'com.github.domm-pixel:milestone_progress_view:1.0.0'
}
```

## 사용 방법 (Usage)

### XML에서 사용

```xml
<com.dommpixel.milestoneprogressview.MilestoneProgressView
    android:id="@+id/milestoneProgressView"
    android:layout_width="match_parent"
    android:layout_height="80dp"
    android:padding="8dp"
    app:progressColor="#2196F3"
    app:backgroundColor="#E0E0E0"
    app:milestoneColor="#4CAF50"
    app:milestoneIncompleteColor="#9E9E9E"
    app:textColor="#333333"
    app:progressBarHeight="8dp"
    app:milestoneRadius="12dp"
    app:textSize="14sp"
    app:progress="0.3" />
```

### 코틀린에서 사용

```kotlin
import com.dommpixel.milestoneprogressview.MilestoneProgressView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val milestoneProgressView = findViewById<MilestoneProgressView>(R.id.milestoneProgressView)
        
        // 마일스톤 설정
        val milestones = listOf(
            MilestoneProgressView.Milestone(0.0f, "시작", true),
            MilestoneProgressView.Milestone(0.25f, "기획", true),
            MilestoneProgressView.Milestone(0.5f, "개발", false),
            MilestoneProgressView.Milestone(0.75f, "테스트", false),
            MilestoneProgressView.Milestone(1.0f, "출시", false)
        )
        
        milestoneProgressView.setMilestones(milestones)
        milestoneProgressView.setProgress(0.3f)
        
        // 애니메이션으로 진행도 변경
        milestoneProgressView.animateProgress(0.8f, 2000L)
    }
}
```

### Java에서 사용

```java
import com.dommpixel.milestoneprogressview.MilestoneProgressView;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MilestoneProgressView milestoneProgressView = findViewById(R.id.milestoneProgressView);
        
        // 마일스톤 설정
        List<MilestoneProgressView.Milestone> milestones = Arrays.asList(
            new MilestoneProgressView.Milestone(0.0f, "시작", true),
            new MilestoneProgressView.Milestone(0.25f, "기획", true),
            new MilestoneProgressView.Milestone(0.5f, "개발", false),
            new MilestoneProgressView.Milestone(0.75f, "테스트", false),
            new MilestoneProgressView.Milestone(1.0f, "출시", false)
        );
        
        milestoneProgressView.setMilestones(milestones);
        milestoneProgressView.setProgress(0.3f);
        
        // 애니메이션으로 진행도 변경
        milestoneProgressView.animateProgress(0.8f, 2000L);
    }
}
```

## XML 속성 (XML Attributes)

| 속성 | 타입 | 기본값 | 설명 |
|------|------|--------|------|
| `app:progressColor` | color | `#2196F3` | 진행도 바 색상 |
| `app:backgroundColor` | color | `#E0E0E0` | 배경 바 색상 |
| `app:milestoneColor` | color | `#4CAF50` | 완료된 마일스톤 색상 |
| `app:milestoneIncompleteColor` | color | `#9E9E9E` | 미완료 마일스톤 색상 |
| `app:textColor` | color | `#333333` | 텍스트 색상 |
| `app:progressBarHeight` | dimension | `8dp` | 진행도 바 높이 |
| `app:milestoneRadius` | dimension | `12dp` | 마일스톤 원 반지름 |
| `app:textSize` | dimension | `14sp` | 텍스트 크기 |
| `app:progress` | float | `0.0` | 초기 진행도 (0.0-1.0) |

## API 메서드 (API Methods)

### Milestone 클래스

```kotlin
data class Milestone(
    val position: Float,      // 위치 (0.0-1.0)
    val label: String,        // 라벨 텍스트
    val isCompleted: Boolean = false  // 완료 여부
)
```

### MilestoneProgressView 메서드

```kotlin
// 마일스톤 설정
fun setMilestones(milestones: List<Milestone>)

// 진행도 설정 (0.0-1.0)
fun setProgress(progress: Float)

// 애니메이션으로 진행도 변경
fun animateProgress(targetProgress: Float, duration: Long = 1000L)

// 속성 설정
var progressColor: Int
var backgroundColor: Int
var milestoneColor: Int
var milestoneIncompleteColor: Int
var textColor: Int
var progressBarHeight: Float
var milestoneRadius: Float
var textSize: Float
```

## 예제 프로젝트 (Example Project)

이 저장소의 `app` 모듈에서 완전한 예제를 확인할 수 있습니다:

1. 프로젝트 클론: `git clone https://github.com/domm-pixel/milestone_progress_view.git`
2. Android Studio에서 프로젝트 열기
3. 앱 실행하여 다양한 예제 확인

## 라이센스 (License)

```
MIT License

Copyright (c) 2024 domm-pixel

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 기여하기 (Contributing)

1. 이 저장소를 포크합니다
2. 새로운 기능 브랜치를 생성합니다 (`git checkout -b feature/AmazingFeature`)
3. 변경사항을 커밋합니다 (`git commit -m 'Add some AmazingFeature'`)
4. 브랜치에 푸시합니다 (`git push origin feature/AmazingFeature`)
5. Pull Request를 생성합니다

## 지원 (Support)

문제가 있거나 제안사항이 있으시면 [Issues](https://github.com/domm-pixel/milestone_progress_view/issues)에 등록해 주세요.

## 배포 방법 (Publishing)

### JitPack을 통한 자동 배포

이 라이브러리는 JitPack을 통해 자동으로 배포됩니다:

1. GitHub에서 새로운 릴리스 생성 (태그 형식: `v1.0.0`)
2. JitPack이 자동으로 라이브러리를 빌드하고 배포
3. 사용자들이 `implementation 'com.github.domm-pixel:milestone_progress_view:1.0.0'`으로 사용 가능

### 로컬 Maven 저장소에 게시

```bash
./gradlew :library:publishToMavenLocal
```

### 수동 배포 과정

1. 버전 업데이트: `library/build.gradle`에서 `version` 변경
2. 라이브러리 빌드: `./gradlew :library:build`
3. GitHub에서 릴리스 생성
4. JitPack에서 자동 빌드 확인

---

⭐ 이 프로젝트가 도움이 되셨다면 스타를 눌러주세요!
